/*
 * Copyright (c) 2025 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.server

import com.fasterxml.jackson.databind.ObjectMapper
import io.javalin.Javalin
import io.javalin.config.JavalinConfig
import io.javalin.http.Context
import io.javalin.json.JavalinJackson
import pl.jwizard.jwl.i18n.I18nBean
import pl.jwizard.jwl.i18n.source.I18nGeneralServerExceptionSource
import pl.jwizard.jwl.ioc.CleanupAfterIoCDestroy
import pl.jwizard.jwl.ioc.IoCKtContextFactory
import pl.jwizard.jwl.ioc.stereotype.SingletonComponent
import pl.jwizard.jwl.property.AppBaseProperty
import pl.jwizard.jwl.property.BaseEnvironment
import pl.jwizard.jwl.server.attribute.CommonServerAttribute
import pl.jwizard.jwl.server.exception.ExceptionsAdvisorBase
import pl.jwizard.jwl.server.exception.HttpErrorResponseDto
import pl.jwizard.jwl.server.filter.WebFilterBase
import pl.jwizard.jwl.server.route.RestControllerBase
import pl.jwizard.jwl.util.logger

/**
 * Main HTTP server component that manages Javalin server configuration, routes, and exception handling.
 *
 * This class configures the server with controllers, filters, and exception handlers, and handles internationalization
 * for error messages.
 *
 * @property environment The application environment containing server properties.
 * @property ioCKtContextFactory The context factory for dependency injection and managing application beans.
 * @property i18nBean Bean for handling internationalization and localization of messages.
 * @property objectMapper A Jackson ObjectMapper for JSON serialization and deserialization.
 * @author Miłosz Gilga
 */
@SingletonComponent
class HttpServer(
	private val environment: BaseEnvironment,
	private val ioCKtContextFactory: IoCKtContextFactory,
	private val i18nBean: I18nBean,
	private val objectMapper: ObjectMapper,
) : CleanupAfterIoCDestroy {

	companion object {
		private val log = logger<HttpServer>()

		/**
		 * Endpoint for the health check.
		 */
		private const val ACTUATOR_HEALTH_ENDPOINT = "/actuator/health"
	}

	/**
	 * Javalin server instance
	 */
	private lateinit var server: Javalin

	/**
	 * Initializes the HTTP server, configuring routes, filters, and exception handlers. This method optionally accepts
	 * a server hook and custom configurations.
	 *
	 * @param serverHook An optional hook triggered after the server starts, allowing additional setup.
	 * @param withCustomConfig Vararg for custom Javalin configuration functions.
	 */
	fun init(serverHook: HttpServerHook?, vararg withCustomConfig: (JavalinConfig) -> Unit) {
		server = Javalin
			.create { config ->
				config.showJavalinBanner = false
				config.jsonMapper(JavalinJackson(objectMapper))
				withCustomConfig.forEach { it(config) }
			}
			.events { event ->
				event.serverStarted { serverHook?.afterStartServer(ioCKtContextFactory) }
			}
		val filters = ioCKtContextFactory.getBeansWithSupertype<WebFilterBase>()
		for (filter in filters) {
			server.addHttpHandler(filter.type.handlerType, filter.matcher, filter::filter)
			log.info("Load web filter: {} -> \"{}\" (type: {}).", filter::class.qualifiedName, filter.matcher, filter.type)
		}
		val exceptions = ioCKtContextFactory.getBeansWithSupertype<ExceptionsAdvisorBase<Exception>>()
		for (ex in exceptions) {
			server.exception(ex.clazz.java, ex::executeStatement)
			log.info("Load exception advisor: {} (exception type: {}).", ex::class.qualifiedName, ex::class.simpleName)
		}
		val controllers = ioCKtContextFactory.getBeansWithSupertype<RestControllerBase>()
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
		for (exception in I18nGeneralServerExceptionSource.entries.filter(I18nGeneralServerExceptionSource::withHandler)) {
			server.error(exception.statusCode) { ctx ->
				val locale = ctx.getAttribute<String>(CommonServerAttribute.I18N_LOCALE)
				val response = HttpErrorResponseDto(exception.statusCode, i18nBean.t(exception, locale))
				ctx.json(response).status(exception.statusCode)
			}
			log.info("Load status code: {} handler.", exception.statusCode)
		}
		server.get(ACTUATOR_HEALTH_ENDPOINT) { ctx -> ctx.json(ActuatorHealthResDto("UP")) }
		log.info("Load actuator with endpoint: \"{}\".", ACTUATOR_HEALTH_ENDPOINT)

		val serverPort = environment.getProperty<Int>(AppBaseProperty.SERVER_PORT)
		server.start(serverPort)
	}

	/**
	 * Initializes the HTTP server with custom configuration, without a server hook.
	 *
	 * @param withCustomConfig Optional configuration functions for JavalinConfig.
	 */
	fun init(vararg withCustomConfig: (JavalinConfig) -> Unit) = init(null, *withCustomConfig)

	/**
	 * Stops the server, releasing all resources and shutting down the application. This method is called when the bean
	 * is destroyed by the Spring context.
	 */
	override fun destroy() {
		server.stop()
	}
}
