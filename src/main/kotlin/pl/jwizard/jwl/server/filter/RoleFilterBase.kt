package pl.jwizard.jwl.server.filter

import io.javalin.http.Context
import io.javalin.security.RouteRole

abstract class RoleFilterBase : WebFilterBase() {
	override val type = WebFilterType.BEFORE_MATCHED

	override fun filter(ctx: Context) {
		if (ctx.routeRoles().containsAll(roles.toList())) {
			roleFilter(ctx)
		}
	}

	abstract val roles: Array<RouteRole>

	protected abstract fun roleFilter(ctx: Context)
}
