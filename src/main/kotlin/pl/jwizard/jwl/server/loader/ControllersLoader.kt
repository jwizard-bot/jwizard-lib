package pl.jwizard.jwl.server.loader

import io.javalin.Javalin
import pl.jwizard.jwl.ioc.IoCKtContextFactory
import pl.jwizard.jwl.server.route.HttpControllerBase
import pl.jwizard.jwl.server.route.RouteMethod

internal class ControllersLoader(
	private val ioCKtContextFactory: IoCKtContextFactory,
	javalin: Javalin,
) : ComponentsLoader<HttpControllerBase>(javalin) {
	override val name = "http controller"

	override val extractor = {
		ioCKtContextFactory.getBeansWithSupertype<HttpControllerBase>()
	}

	override fun loadComponent(javalin: Javalin, component: HttpControllerBase): List<String> {
		val routes = component.routes
		for (route in routes) {
			val rolesFromFilters = route.handler.withRoles
				.filterValues { it.map(RouteMethod::handlerType).contains(route.method) || it.isEmpty() }
				.keys
			val combinedRoles = (rolesFromFilters + route.roles).toTypedArray()
			javalin.addHttpHandler(route.method, component.basePath + route.path, { ctx ->
				route.handler.handle(ctx)
			}, *combinedRoles)
		}
		return listOf(
			"path: \"${component.basePath}\"",
			"routes count: ${routes.size}",
			"routes: $routes",
		)
	}
}
