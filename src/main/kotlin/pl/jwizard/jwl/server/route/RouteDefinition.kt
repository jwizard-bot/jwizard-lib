/*
 * Copyright (c) 2025 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.server.route

import io.javalin.http.HandlerType

/**
 * Defines an HTTP route with a specified path, method, handler, and roles.
 *
 * @param T The type of handler used for processing requests.
 * @property path The endpoint path of the route.
 * @property method The HTTP method (ex. GET, POST) associated with this route.
 * @property handler The request handler responsible for processing requests.
 * @property roles The roles permitted to access this route.
 * @author Miłosz Gilga
 */
class RouteDefinition<T>(
	val path: String,
	val method: HandlerType,
	val handler: T,
	val roles: Array<ServerRole>,
) {

	/**
	 * Returns the string representation of the route's path.
	 *
	 * @return The path of the route.
	 */
	override fun toString() = path
}
