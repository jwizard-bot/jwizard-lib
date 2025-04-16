package pl.jwizard.jwl.server.loader

import io.javalin.Javalin
import pl.jwizard.jwl.ioc.IoCKtContextFactory
import pl.jwizard.jwl.server.filter.FilterRole
import pl.jwizard.jwl.server.filter.RoleFilterBase
import pl.jwizard.jwl.server.filter.WebFilterBase
import kotlin.reflect.full.superclasses

internal class FiltersLoader(
	private val ioCKtContextFactory: IoCKtContextFactory,
	javalin: Javalin,
) : ComponentsLoader<WebFilterBase>(javalin) {
	override val name = "http filter"

	override val extractor = {
		ioCKtContextFactory.getBeansWithSupertype<WebFilterBase>()
	}

	override fun componentTransformation(
		components: List<WebFilterBase>,
	) = components.sortedBy { it.runIndex }

	override fun loadComponent(javalin: Javalin, component: WebFilterBase): List<String> {
		javalin.addHttpHandler(component.type.handlerType, component.matcher, component::filter)

		val subClasses = component::class.superclasses.map { it.simpleName }
		val logElements = mutableListOf<String>()

		logElements += "path: \"${component.matcher}\""
		logElements += "superclass: $subClasses"
		if (component is RoleFilterBase) {
			logElements += "roles: ${(component.roles.map(FilterRole::id))}"
		}
		logElements += "type: ${component.type}"
		logElements += "run index: ${component.runIndex}"

		return logElements.toList()
	}
}
