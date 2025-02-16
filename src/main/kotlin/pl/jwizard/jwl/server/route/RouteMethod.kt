package pl.jwizard.jwl.server.route

import io.javalin.http.HandlerType

enum class RouteMethod(val handlerType: HandlerType) {
	GET(HandlerType.GET),
	POST(HandlerType.POST),
	PUT(HandlerType.PUT),
	DELETE(HandlerType.DELETE),
	PATCH(HandlerType.PATCH),
	;
}
