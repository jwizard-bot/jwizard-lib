/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.server.route

import io.javalin.http.Handler
import io.javalin.http.HandlerType

/**
 * Defines a route in the application, including the path, HTTP method, handler, and the associated roles.
 *
 * @property path The path of the route (ex. "/users").
 * @property method The HTTP method associated with the route (ex. GET, POST, etc.).
 * @property handler The handler to be executed when the route is matched.
 * @property roles The roles required to access the route.
 * @author Miłosz Gilga
 */
class RouteDefinition private constructor(
	val path: String,
	val method: HandlerType,
	val handler: Handler,
	val roles: Array<ServerRole>,
) {

	/**
	 * Builder class for constructing multiple route definitions.
	 */
	class Builder {

		/**
		 * A list to hold the created route definitions.
		 */
		private val routes = mutableListOf<RouteDefinition>()

		/**
		 * Adds a GET route to the list of routes.
		 *
		 * @param path The path of the route (ex. "/users").
		 * @param handler The handler to be executed for this route.
		 * @param roles The roles required to access this route.
		 * @return The builder instance for chaining.
		 */
		fun get(path: String, handler: Handler, vararg roles: ServerRole) = apply {
			routes.add(RouteDefinition(path, HandlerType.GET, handler, arrayOf(*roles)))
		}

		/**
		 * Adds a POST route to the list of routes.
		 *
		 * @param path The path of the route (ex. "/users").
		 * @param handler The handler to be executed for this route.
		 * @param roles The roles required to access this route.
		 * @return The builder instance for chaining.
		 */
		fun post(path: String, handler: Handler, vararg roles: ServerRole) = apply {
			routes.add(RouteDefinition(path, HandlerType.POST, handler, arrayOf(*roles)))
		}

		/**
		 * Adds a PUT route to the list of routes.
		 *
		 * @param path The path of the route (ex. "/users").
		 * @param handler The handler to be executed for this route.
		 * @param roles The roles required to access this route.
		 * @return The builder instance for chaining.
		 */
		fun put(path: String, handler: Handler, vararg roles: ServerRole) = apply {
			routes.add(RouteDefinition(path, HandlerType.PUT, handler, arrayOf(*roles)))
		}

		/**
		 * Adds a PATCH route to the list of routes.
		 *
		 * @param path The path of the route (ex. "/users").
		 * @param handler The handler to be executed for this route.
		 * @param roles The roles required to access this route.
		 * @return The builder instance for chaining.
		 */
		fun patch(path: String, handler: Handler, vararg roles: ServerRole) = apply {
			routes.add(RouteDefinition(path, HandlerType.PATCH, handler, arrayOf(*roles)))
		}

		/**
		 * Adds a DELETE route to the list of routes.
		 *
		 * @param path The path of the route (ex. "/users").
		 * @param handler The handler to be executed for this route.
		 * @param roles The roles required to access this route.
		 * @return The builder instance for chaining.
		 */
		fun delete(path: String, handler: Handler, vararg roles: ServerRole) = apply {
			routes.add(RouteDefinition(path, HandlerType.DELETE, handler, arrayOf(*roles)))
		}

		/**
		 * Returns an array of all the defined routes.
		 *
		 * @return An array containing all the route definitions.
		 */
		fun compositeRoutes() = routes.toTypedArray()
	}

	/**
	 * Returns the string representation of the route's path.
	 *
	 * @return The path of the route.
	 */
	override fun toString() = path
}
