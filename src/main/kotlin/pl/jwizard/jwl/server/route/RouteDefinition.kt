package pl.jwizard.jwl.server.route

import io.javalin.http.HandlerType
import io.javalin.security.RouteRole
import pl.jwizard.jwl.server.route.handler.Handler

class RouteDefinition<T : Handler>(
	val path: String,
	val method: HandlerType,
	val handler: T,
	val roles: Array<RouteRole>,
) {
	override fun toString() = path
}
