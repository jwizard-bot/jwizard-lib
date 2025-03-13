package pl.jwizard.jwl.server.route

import io.javalin.http.HandlerType
import io.javalin.security.RouteRole
import pl.jwizard.jwl.server.route.handler.Handler

class RouteDefinitionBuilder {
	private val routes = mutableListOf<RouteDefinition<Handler>>()

	fun <T : Handler> get(path: String, handler: T, vararg roles: RouteRole) = apply {
		routes.add(RouteDefinition(path, HandlerType.GET, handler, arrayOf(*roles)))
	}

	fun <T : Handler> post(path: String, handler: T, vararg roles: RouteRole) = apply {
		routes.add(RouteDefinition(path, HandlerType.POST, handler, arrayOf(*roles)))
	}

	fun <T : Handler> put(path: String, handler: T, vararg roles: RouteRole) = apply {
		routes.add(RouteDefinition(path, HandlerType.PUT, handler, arrayOf(*roles)))
	}

	fun <T : Handler> patch(path: String, handler: T, vararg roles: RouteRole) = apply {
		routes.add(RouteDefinition(path, HandlerType.PATCH, handler, arrayOf(*roles)))
	}

	fun <T : Handler> delete(path: String, handler: T, vararg roles: RouteRole) = apply {
		routes.add(RouteDefinition(path, HandlerType.DELETE, handler, arrayOf(*roles)))
	}

	fun compositeRoutes() = routes.toList()
}
