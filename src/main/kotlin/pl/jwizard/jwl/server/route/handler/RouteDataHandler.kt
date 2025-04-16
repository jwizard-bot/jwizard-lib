package pl.jwizard.jwl.server.route.handler

import io.javalin.http.Context
import pl.jwizard.jwl.server.filter.FilterRole
import pl.jwizard.jwl.server.route.RouteMethod

abstract class RouteDataHandler<T> : Handler() {
	protected abstract val callback: (Context, T) -> Unit

	protected abstract fun handleWithData(ctx: Context): T

	override val withRoles = emptyMap<FilterRole, List<RouteMethod>>()

	final override fun handle(ctx: Context) {
		callback(ctx, handleWithData(ctx))
	}
}
