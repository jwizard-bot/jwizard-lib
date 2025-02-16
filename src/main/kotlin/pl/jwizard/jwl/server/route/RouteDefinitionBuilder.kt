package pl.jwizard.jwl.server.route

import io.javalin.http.Handler
import io.javalin.http.HandlerType
import pl.jwizard.jwl.server.I18nExtHandler

class RouteDefinitionBuilder {
	private val routes = mutableListOf<RouteDefinition<Handler>>()
	private val routesWithI18n = mutableListOf<RouteDefinition<I18nExtHandler>>()

	fun get(path: String, handler: Handler, vararg roles: ServerRole) = apply {
		routes.add(RouteDefinition(path, HandlerType.GET, handler, arrayOf(*roles)))
	}

	fun getWithI18n(path: String, handler: I18nExtHandler, vararg roles: ServerRole) = apply {
		routesWithI18n.add(RouteDefinition(path, HandlerType.GET, handler, arrayOf(*roles)))
	}

	fun post(path: String, handler: Handler, vararg roles: ServerRole) = apply {
		routes.add(RouteDefinition(path, HandlerType.POST, handler, arrayOf(*roles)))
	}

	fun postWithI18n(path: String, handler: I18nExtHandler, vararg roles: ServerRole) = apply {
		routesWithI18n.add(RouteDefinition(path, HandlerType.POST, handler, arrayOf(*roles)))
	}

	fun put(path: String, handler: Handler, vararg roles: ServerRole) = apply {
		routes.add(RouteDefinition(path, HandlerType.PUT, handler, arrayOf(*roles)))
	}

	fun put(path: String, handler: I18nExtHandler, vararg roles: ServerRole) = apply {
		routesWithI18n.add(RouteDefinition(path, HandlerType.PUT, handler, arrayOf(*roles)))
	}

	fun patch(path: String, handler: Handler, vararg roles: ServerRole) = apply {
		routes.add(RouteDefinition(path, HandlerType.PATCH, handler, arrayOf(*roles)))
	}

	fun patch(path: String, handler: I18nExtHandler, vararg roles: ServerRole) = apply {
		routesWithI18n.add(RouteDefinition(path, HandlerType.PATCH, handler, arrayOf(*roles)))
	}

	fun delete(path: String, handler: Handler, vararg roles: ServerRole) = apply {
		routes.add(RouteDefinition(path, HandlerType.DELETE, handler, arrayOf(*roles)))
	}

	fun delete(path: String, handler: I18nExtHandler, vararg roles: ServerRole) = apply {
		routesWithI18n.add(RouteDefinition(path, HandlerType.DELETE, handler, arrayOf(*roles)))
	}

	fun compositeRoutes() = CompositedRoutes(routes.toTypedArray(), routesWithI18n.toTypedArray())
}
