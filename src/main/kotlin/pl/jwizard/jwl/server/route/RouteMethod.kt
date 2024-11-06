/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.server.route

import io.javalin.http.HandlerType

/**
 * Enum representing HTTP methods used for defining routes in the server. Each method corresponds to a specific type
 * of HTTP request.
 *
 * @property handlerType The [HandlerType] associated with the HTTP method.
 * @author Miłosz Gilga
 */
enum class RouteMethod(val handlerType: HandlerType) {

	/**
	 * Represents the HTTP GET method, typically used for retrieving data.
	 */
	GET(HandlerType.GET),

	/**
	 * Represents the HTTP POST method, typically used for submitting data.
	 */
	POST(HandlerType.POST),

	/**
	 * Represents the HTTP PUT method, typically used for updating data.
	 */
	PUT(HandlerType.PUT),

	/**
	 * Represents the HTTP DELETE method, typically used for deleting data.
	 */
	DELETE(HandlerType.DELETE),

	/**
	 * Represents the HTTP PATCH method, typically used for partially updating data.
	 */
	PATCH(HandlerType.PATCH),
}
