package pl.jwizard.jwl.server.filter

import io.javalin.http.HandlerType

enum class WebFilterType(val handlerType: HandlerType) {
	// applied before any request
	BEFORE(HandlerType.BEFORE),

	// applied before matched request to path
	BEFORE_MATCHED(HandlerType.BEFORE_MATCHED),

	// applied after any request
	AFTER(HandlerType.AFTER),

	// applied after matched request to path
	AFTER_MATCHED(HandlerType.AFTER_MATCHED),
	;
}
