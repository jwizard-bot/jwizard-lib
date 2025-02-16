package pl.jwizard.jwl.server.exception

import io.javalin.http.HttpStatus
import pl.jwizard.jwl.i18n.I18nLocaleSource

data class ExceptionResponse(
	val status: HttpStatus,
	val i18nLocaleSource: I18nLocaleSource,
	val args: Map<String, Any?> = emptyMap(),
	val details: Map<String, List<String>> = emptyMap(),
)
