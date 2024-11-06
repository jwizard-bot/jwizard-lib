/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.server.filter

import io.javalin.http.Context

/**
 * Interface that defines the contract for web filters in Javalin. Filters allow you to intercept and manipulate HTTP
 * requests and responses before or after they are processed by route handlers. The filters can be applied based on
 * their type and a matching pattern.
 *
 * @author Miłosz Gilga
 */
interface WebFilterBase {

	/**
	 * The type of the filter. This defines when the filter will be applied in the request/response lifecycle. The filter
	 * type can be one of [WebFilterType], such as BEFORE or AFTER.
	 */
	val type: WebFilterType

	/**
	 * The matcher string used to determine which routes this filter should apply to. It can be a URL pattern or a
	 * regular expression, depending on the configuration.
	 */
	val matcher: String

	/**
	 * The filter function that processes incoming requests. It is invoked during the request handling process for the
	 * routes matched by the filter's matcher.
	 *
	 * @param ctx The [Context] object that represents the HTTP request and response.
	 */
	fun filter(ctx: Context)
}
