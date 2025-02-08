/*
 * Copyright (c) 2025 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.server.route

import io.javalin.http.Handler
import io.javalin.http.HandlerType
import pl.jwizard.jwl.server.I18nExtHandler

/**
 * Builder class for defining and managing HTTP routes, including both standard and internationalized (i18n) routes.
 *
 * This class allows for the easy creation and organization of route definitions using method chaining.
 *
 * @author Miłosz Gilga

 */
class RouteDefinitionBuilder {

	/**
	 * A list to hold the created route definitions.
	 */
	private val routes = mutableListOf<RouteDefinition<Handler>>()

	/**
	 * A list to hold the created route definitions using i18n handlers.
	 */
	private val routesWithI18n = mutableListOf<RouteDefinition<I18nExtHandler>>()

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
	 * Adds a GET route with i18n support to the list of routes.
	 *
	 * @param path The path of the route.
	 * @param handler The i18n handler for this route.
	 * @param roles The roles required to access this route.
	 * @return The builder instance for chaining.
	 */
	fun getWithI18n(path: String, handler: I18nExtHandler, vararg roles: ServerRole) = apply {
		routesWithI18n.add(RouteDefinition(path, HandlerType.GET, handler, arrayOf(*roles)))
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
	 * Adds a POST route to the list of routes.
	 *
	 * @param path The path of the route.
	 * @param handler The handler to be executed for this route.
	 * @param roles The roles required to access this route.
	 * @return The builder instance for chaining.
	 */
	fun postWithI18n(path: String, handler: I18nExtHandler, vararg roles: ServerRole) = apply {
		routesWithI18n.add(RouteDefinition(path, HandlerType.POST, handler, arrayOf(*roles)))
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
	 * Adds a PUT route with i18n support to the list of routes.
	 *
	 * @param path The path of the route.
	 * @param handler The i18n handler for this route.
	 * @param roles The roles required to access this route.
	 * @return The builder instance for chaining.
	 */
	fun put(path: String, handler: I18nExtHandler, vararg roles: ServerRole) = apply {
		routesWithI18n.add(RouteDefinition(path, HandlerType.PUT, handler, arrayOf(*roles)))
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
	 * Adds a PUT route with i18n support to the list of routes.
	 *
	 * @param path The path of the route.
	 * @param handler The i18n handler for this route.
	 * @param roles The roles required to access this route.
	 * @return The builder instance for chaining.
	 */
	fun patch(path: String, handler: I18nExtHandler, vararg roles: ServerRole) = apply {
		routesWithI18n.add(RouteDefinition(path, HandlerType.PATCH, handler, arrayOf(*roles)))
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
	 * Adds a DELETE route with i18n support to the list of routes.
	 *
	 * @param path The path of the route.
	 * @param handler The i18n handler for this route.
	 * @param roles The roles required to access this route.
	 * @return The builder instance for chaining.
	 */
	fun delete(path: String, handler: I18nExtHandler, vararg roles: ServerRole) = apply {
		routesWithI18n.add(RouteDefinition(path, HandlerType.DELETE, handler, arrayOf(*roles)))
	}

	/**
	 * Returns an array of all the defined routes, both standard and i18n-enabled.
	 *
	 * @return An array containing all the route definitions.
	 */
	fun compositeRoutes() = CompositedRoutes(routes.toTypedArray(), routesWithI18n.toTypedArray())
}
