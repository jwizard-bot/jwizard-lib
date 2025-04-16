package pl.jwizard.jwl.server.filter

import io.javalin.security.RouteRole

interface FilterRole : RouteRole {
	val id: String
}
