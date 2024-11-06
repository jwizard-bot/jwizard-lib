/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.server.route

import io.javalin.security.RouteRole

/**
 * Enum representing roles within the server's route security configuration. Implements [RouteRole] to enable
 * role-based access control for routes.
 *
 * @author Miłosz Gilga
 */
enum class ServerRole : RouteRole {

	/**
	 * Role indicating that the access to this route must be authenticated. Used to restrict access to routes requiring
	 * authenticated user sessions.
	 */
	AUTHENTICATED,
	;
}
