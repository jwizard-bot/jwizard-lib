package pl.jwizard.jwl.server.route

import io.javalin.http.HandlerType
import pl.jwizard.jwl.server.filter.FilterRole
import pl.jwizard.jwl.server.route.handler.Handler

class RouteDefinition<T : Handler>(
	val path: String,
	val method: HandlerType,
	val handler: T,
	val roles: Array<FilterRole>,
) {
	override fun toString() = path
}
