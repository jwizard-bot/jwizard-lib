/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.server.exception

import io.javalin.http.HttpStatus
import pl.jwizard.jwl.i18n.I18nLocaleSource

/**
 * A data class representing an exception response, which includes information about the HTTP status, the translated
 * message source, and optional arguments for the message.
 *
 * @property status The HTTP status code associated with the exception.
 * @property i18nLocaleSource The source for the translated error message.
 * @property args A map of arguments that can be used for dynamic message generation (optional).
 * @author Miłosz Gilga
 */
data class ExceptionResponse(
	val status: HttpStatus,
	val i18nLocaleSource: I18nLocaleSource,
	val args: Map<String, Any?> = emptyMap(),
)
