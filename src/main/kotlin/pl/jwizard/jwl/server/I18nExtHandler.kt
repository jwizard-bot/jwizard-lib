package pl.jwizard.jwl.server

import io.javalin.http.Context

fun interface I18nExtHandler {
	fun handle(ctx: Context, language: String?)
}
