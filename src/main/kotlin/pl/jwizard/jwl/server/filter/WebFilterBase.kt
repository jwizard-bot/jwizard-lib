package pl.jwizard.jwl.server.filter

import io.javalin.http.Context

abstract class WebFilterBase {
	// filter invoking schedule type
	open val type = WebFilterType.BEFORE

	// filter path
	open val matcher = "/*"

	// determinate sequence of run filters
	open val runIndex = 0

	abstract fun filter(ctx: Context)
}
