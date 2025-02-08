/*
 * Copyright (c) 2025 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.server.route

/**
 * Interface that defines a base contract for REST controllers. It provides a base path and a set of routes for the
 * controller.
 *
 * @author Miłosz Gilga
 */
interface RestControllerBase {

	/**
	 * The base path for the REST controller. This is the common prefix for all routes in this controller (ex. "/users",
	 * "/products").
	 */
	val basePath: String

	/**
	 * The routes that belong to this REST controller. This array defines the individual route paths and their associated
	 * methods (GET, POST, etc.) and handlers.
	 */
	val routes: CompositedRoutes
}
