package pl.jwizard.jwl.server.route.handler

import io.javalin.http.Context
import pl.jwizard.jwl.server.filter.FilterRole
import pl.jwizard.jwl.server.route.RouteMethod

class RouteHandler(val callback: (Context) -> Unit) : Handler() {
	override val withRoles = emptyMap<FilterRole, List<RouteMethod>>()

	override fun handle(ctx: Context) = callback(ctx)
}
