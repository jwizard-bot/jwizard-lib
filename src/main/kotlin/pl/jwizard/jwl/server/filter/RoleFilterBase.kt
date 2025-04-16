package pl.jwizard.jwl.server.filter

import io.javalin.http.Context

abstract class RoleFilterBase : WebFilterBase() {
	override val type = WebFilterType.BEFORE_MATCHED

	override fun filter(ctx: Context) {
		if (ctx.routeRoles().containsAll(roles.toList())) {
			roleFilter(ctx)
		}
	}

	abstract val roles: Array<FilterRole>

	protected abstract fun roleFilter(ctx: Context)
}
