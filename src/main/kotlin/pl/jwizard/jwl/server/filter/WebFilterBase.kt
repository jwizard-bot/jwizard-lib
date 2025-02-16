package pl.jwizard.jwl.server.filter

import io.javalin.http.Context

interface WebFilterBase {
	// filter invoking schedule type
	val type: WebFilterType

	// filter path
	val matcher: String

	fun filter(ctx: Context)
}
