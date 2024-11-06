/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.server.filter

import io.javalin.http.HandlerType

/**
 * Enum representing different types of web filters that can be applied to Javalin routes. Each filter type corresponds
 * to a specific stage in the request/response lifecycle.
 *
 * @property handlerType The handler type associated with this filter.
 * @author Miłosz Gilga
 */
enum class WebFilterType(val handlerType: HandlerType) {

	/**
	 * A filter that is applied before the request is matched to a handler.
	 */
	BEFORE(HandlerType.BEFORE),

	/**
	 * A filter that is applied before the request is matched to a handler, but after some basic filtering.
	 */
	BEFORE_MATCHED(HandlerType.BEFORE_MATCHED),

	/**
	 * A filter that is applied after the handler has processed the request but before the response is sent.
	 */
	AFTER(HandlerType.AFTER),

	/**
	 * A filter that is applied after the handler has processed the request, after all routes have been matched.
	 */
	AFTER_MATCHED(HandlerType.AFTER_MATCHED),
	;
}
