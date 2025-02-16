package pl.jwizard.jwl.server.route

import io.javalin.http.HandlerType

class RouteDefinition<T>(
	val path: String,
	val method: HandlerType,
	val handler: T,
	val roles: Array<ServerRole>,
) {
	override fun toString() = path
}
