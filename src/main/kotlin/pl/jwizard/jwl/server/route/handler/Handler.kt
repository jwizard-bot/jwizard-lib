package pl.jwizard.jwl.server.route.handler

import io.javalin.http.Context
import io.javalin.security.RouteRole
import pl.jwizard.jwl.server.route.RouteMethod

abstract class Handler {
	protected fun forAllRouteMethods(role: RouteRole) = role to emptyList<RouteMethod>()

	protected fun forRouteMethods(
		role: RouteRole,
		vararg routeMethods: RouteMethod,
	) = role to routeMethods.toList()

	abstract val withRoles: Map<RouteRole, List<RouteMethod>>

	abstract fun handle(ctx: Context)
}
