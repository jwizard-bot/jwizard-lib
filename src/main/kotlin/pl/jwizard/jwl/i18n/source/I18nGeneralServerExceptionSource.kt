/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.i18n.source

import pl.jwizard.jwl.i18n.I18nLocaleSource

/**
 * Enum representing general server exception sources for internationalization (i18n). Each entry in this enum
 * corresponds to a specific HTTP status code and a placeholder key for localization, which maps to the actual error
 * message in the appropriate language.
 *
 * @property statusCode The HTTP status code that corresponds to the specific exception.
 * @property withHandler Determine, if custom exception has dynamically loaded error code handler at server startup.
 * @property placeholder A string key used for localization, which maps to the actual error message.
 * @author Miłosz Gilga
 * @see I18nLocaleSource
 */
enum class I18nGeneralServerExceptionSource(
	val statusCode: Int,
	val withHandler: Boolean,
	override val placeholder: String,
) : I18nLocaleSource {
	BAD_REQUEST_EXCEPTION(400, false, "jw.server.exception.badRequestException"),
	UNAUTHORIZED_EXCEPTION(401, true, "jw.server.exception.unauthorizedException"),
	FORBIDDEN_EXCEPTION(403, true, "jw.server.exception.forbiddenException"),
	NOT_FOUND_EXCEPTION(404, true, "jw.server.exception.notFoundException"),
	INTERNAL_SERVER_ERROR(500, true, "jw.server.exception.internalServerException"),
	;
}
