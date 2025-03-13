package pl.jwizard.jwl.server.loader

import io.javalin.Javalin
import pl.jwizard.jwl.ioc.IoCKtContextFactory
import pl.jwizard.jwl.server.exception.ExceptionAdvisor

internal class ExceptionAdvisorsLoader(
	private val ioCKtContextFactory: IoCKtContextFactory,
	javalin: Javalin,
) : ComponentsLoader<ExceptionAdvisor<Exception>>(javalin) {
	override val name = "exception advisor"

	override val extractor = {
		ioCKtContextFactory.getBeansWithSupertype<ExceptionAdvisor<Exception>>()
	}

	override fun loadComponent(
		javalin: Javalin,
		component: ExceptionAdvisor<Exception>,
	): List<String> {
		javalin.exception(component.clazz.java, component::executeStatement)
		return listOf("exception type: ${component::class.simpleName}")
	}
}
