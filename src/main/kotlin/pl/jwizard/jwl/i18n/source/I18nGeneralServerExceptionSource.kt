package pl.jwizard.jwl.i18n.source

import pl.jwizard.jwl.i18n.I18nLocaleSource

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
