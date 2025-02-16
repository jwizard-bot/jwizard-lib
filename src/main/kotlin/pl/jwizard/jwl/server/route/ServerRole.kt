package pl.jwizard.jwl.server.route

import io.javalin.security.RouteRole

enum class ServerRole : RouteRole {
	AUTHENTICATED,
	;
}
