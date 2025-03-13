package pl.jwizard.jwl.server.route.handler

import io.javalin.http.Context
import io.javalin.security.RouteRole
import pl.jwizard.jwl.server.route.RouteMethod

class RouteHandler(val callback: (Context) -> Unit) : Handler() {
	override val withRoles = emptyMap<RouteRole, List<RouteMethod>>()

	override fun handle(ctx: Context) = callback(ctx)
}
