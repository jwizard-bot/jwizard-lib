package pl.jwizard.jwl.server

import com.fasterxml.jackson.databind.ObjectMapper
import io.javalin.Javalin
import io.javalin.config.JavalinConfig
import io.javalin.http.Context
import io.javalin.json.JavalinJackson
import org.springframework.beans.factory.DisposableBean
import pl.jwizard.jwl.i18n.I18n
import pl.jwizard.jwl.ioc.IoCKtContextFactory
import pl.jwizard.jwl.property.AppBaseProperty
import pl.jwizard.jwl.property.BaseEnvironment
import pl.jwizard.jwl.server.attribute.CommonServerAttribute
import pl.jwizard.jwl.server.exception.ExceptionsAdvisorBase
import pl.jwizard.jwl.server.exception.HttpErrorResponseDto
import pl.jwizard.jwl.server.exception.I18nGeneralServerExceptionSource
import pl.jwizard.jwl.server.filter.WebFilterBase
import pl.jwizard.jwl.server.route.HttpControllerBase
import pl.jwizard.jwl.util.logger

class HttpServer(
	private val environment: BaseEnvironment,
	private val ioCKtContextFactory: IoCKtContextFactory,
	private val i18n: I18n,
	private val objectMapper: ObjectMapper,
) : DisposableBean {
	companion object {
		private val log = logger<HttpServer>()

		private const val ACTUATOR_HEALTH_ENDPOINT = "/actuator/health"
	}

	private lateinit var server: Javalin

	fun init(
		onServerStart: (() -> Unit)? = null,
		vararg withCustomConfig: (JavalinConfig) -> Unit,
	) {
		server = Javalin
			.create { config ->
				config.showJavalinBanner = false
				config.jsonMapper(JavalinJackson(objectMapper))
				withCustomConfig.forEach { it(config) }
			}
			.events { event ->
				event.serverStarted { onServerStart?.let { it() } }
			}
		val filters = ioCKtContextFactory.getBeansWithSupertype<WebFilterBase>()
		for (filter in filters) {
			server.addHttpHandler(filter.type.handlerType, filter.matcher, filter::filter)
			log.info(
				"Load web filter: {} -> \"{}\" (type: {}).",
				filter::class.qualifiedName,
				filter.matcher,
				filter.type,
			)
		}
		val exceptions = ioCKtContextFactory.getBeansWithSupertype<ExceptionsAdvisorBase<Exception>>()
		for (ex in exceptions) {
			server.exception(ex.clazz.java, ex::executeStatement)
			log.info(
				"Load exception advisor: {} (exception type: {}).",
				ex::class.qualifiedName,
				ex::class.simpleName,
			)
		}
		val controllers = ioCKtContextFactory.getBeansWithSupertype<HttpControllerBase>()
		for (controller in controllers) {
			val routes = controller.routes
			routes.baseRoutes.forEach {
				server.addHttpHandler(it.method, controller.basePath + it.path, it.handler, *it.roles)
			}
			routes.routesWithI18n.forEach { route ->
				val handler: (ctx: Context) -> Unit = {
					val locale = it.getAttribute<String>(CommonServerAttribute.I18N_LOCALE)
					route.handler.handle(it, locale)
				}
				server.addHttpHandler(route.method, controller.basePath + route.path, handler, *route.roles)
			}
			log.info(
				"Load controller: {} -> \"{}\" with: {} routes: {}.",
				controller::class.qualifiedName,
				controller.basePath,
				routes.size,
				routes
			)
		}
		for (exception in I18nGeneralServerExceptionSource.entries.filter(
			I18nGeneralServerExceptionSource::withHandler
		)) {
			server.error(exception.statusCode) { ctx ->
				val locale = ctx.getAttribute<String>(CommonServerAttribute.I18N_LOCALE)
				val response = HttpErrorResponseDto(exception.statusCode, i18n.t(exception, locale))
				ctx.json(response).status(exception.statusCode)
			}
			log.info("Load status code: {} handler.", exception.statusCode)
		}
		server.get(ACTUATOR_HEALTH_ENDPOINT) { ctx -> ctx.json(ActuatorHealthResDto("UP")) }
		log.info("Load actuator with endpoint: \"{}\".", ACTUATOR_HEALTH_ENDPOINT)

		val serverPort = environment.getProperty<Int>(AppBaseProperty.SERVER_PORT)
		server.start(serverPort)
	}

	fun init(vararg withCustomConfig: (JavalinConfig) -> Unit) = init(null, *withCustomConfig)

	override fun destroy() {
		server.stop()
	}
}
