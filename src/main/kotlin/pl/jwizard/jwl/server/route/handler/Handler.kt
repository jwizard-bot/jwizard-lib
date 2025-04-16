package pl.jwizard.jwl.server.route.handler

import io.javalin.http.Context
import pl.jwizard.jwl.server.filter.FilterRole
import pl.jwizard.jwl.server.route.RouteMethod

abstract class Handler {
	protected fun forAllRouteMethods(role: FilterRole) = role to emptyList<RouteMethod>()

	protected fun forRouteMethods(
		role: FilterRole,
		vararg routeMethods: RouteMethod,
	) = role to routeMethods.toList()

	abstract val withRoles: Map<FilterRole, List<RouteMethod>>

	abstract fun handle(ctx: Context)
}
