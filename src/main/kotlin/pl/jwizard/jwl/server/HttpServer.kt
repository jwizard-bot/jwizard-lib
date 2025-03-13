package pl.jwizard.jwl.server

import com.fasterxml.jackson.databind.ObjectMapper
import io.javalin.Javalin
import io.javalin.config.JavalinConfig
import io.javalin.json.JavalinJackson
import org.springframework.beans.factory.DisposableBean
import pl.jwizard.jwl.ioc.IoCKtContextFactory
import pl.jwizard.jwl.property.AppBaseProperty
import pl.jwizard.jwl.property.BaseEnvironment
import pl.jwizard.jwl.server.loader.ControllersLoader
import pl.jwizard.jwl.server.loader.ExceptionAdvisorsLoader
import pl.jwizard.jwl.server.loader.FiltersLoader
import pl.jwizard.jwl.server.loader.GeneralExceptionHandlersLoader

class HttpServer(
	private val ioCKtContextFactory: IoCKtContextFactory,
	private val objectMapper: ObjectMapper,
	environment: BaseEnvironment,
) : DisposableBean {
	private val serverPort = environment.getProperty<Int>(AppBaseProperty.SERVER_PORT)

	private lateinit var javalin: Javalin

	fun init(
		onServerStart: (() -> Unit)? = null,
		extendedConfig: ((JavalinConfig) -> Unit)? = null,
	) {
		javalin = Javalin.create { config ->
			config.showJavalinBanner = false
			config.jsonMapper(JavalinJackson(objectMapper))
			extendedConfig?.let { it(config) }
		}
		javalin.events { event -> event.serverStarted { onServerStart?.let { it() } } }

		val filtersLoader = FiltersLoader(ioCKtContextFactory, javalin)
		val controllersLoader = ControllersLoader(ioCKtContextFactory, javalin)
		val exceptionAdvisorsLoader = ExceptionAdvisorsLoader(ioCKtContextFactory, javalin)
		val generalExceptionHandlersLoader = GeneralExceptionHandlersLoader(javalin)

		filtersLoader.initComponents()
		controllersLoader.initComponents()
		exceptionAdvisorsLoader.initComponents()
		generalExceptionHandlersLoader.initComponents()

		javalin.start(serverPort)
	}

	override fun destroy() {
		javalin.stop()
	}
}
