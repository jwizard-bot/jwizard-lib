package pl.jwizard.jwl.server.filter

import io.javalin.http.Context
import pl.jwizard.jwl.server.route.ServerRole

abstract class RoleFilterBase : WebFilterBase {
	override val type = WebFilterType.BEFORE_MATCHED
	override val matcher = "/*"

	override fun filter(ctx: Context) {
		if (ctx.routeRoles().containsAll(roles.toList())) {
			roleFilter(ctx)
		}
	}

	protected abstract val roles: Array<ServerRole>

	protected abstract fun roleFilter(ctx: Context)
}
