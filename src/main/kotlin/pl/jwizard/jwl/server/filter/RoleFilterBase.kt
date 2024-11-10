/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.server.filter

import io.javalin.http.Context
import pl.jwizard.jwl.server.route.ServerRole

/**
 * Abstract class that represents a role-based filter for Javalin routes. This filter intercepts HTTP requests before
 * they are matched by the route and checks if the request has the necessary roles. If the roles are valid, the filter
 * logic is applied.
 *
 * @author Miłosz Gilga
 */
abstract class RoleFilterBase : WebFilterBase {

	override val type = WebFilterType.BEFORE_MATCHED
	override val matcher = "/*"

	/**
	 * The filter function that checks if the route contains the necessary roles. If the roles match, it invokes the
	 * [roleFilter] method to process the request.
	 *
	 * @param ctx The [Context] object that represents the HTTP request and response.
	 */
	override fun filter(ctx: Context) {
		if (ctx.routeRoles().containsAll(roles.toList())) {
			roleFilter(ctx)
		}
	}

	/**
	 * The roles that are required to access the route. These roles will be checked against the roles assigned to the
	 * current route before the filter logic is applied.
	 */
	protected abstract val roles: Array<ServerRole>

	/**
	 * The actual filtering logic that will be applied if the roles match. This method must be implemented in subclasses
	 * to define what should happen when the role check passes.
	 *
	 * @param ctx The [Context] object that represents the HTTP request and response.
	 */
	protected abstract fun roleFilter(ctx: Context)
}
