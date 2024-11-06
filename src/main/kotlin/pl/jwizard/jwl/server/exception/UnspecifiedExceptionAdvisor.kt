/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.server.exception

import io.javalin.http.Context
import io.javalin.http.InternalServerErrorResponse
import org.springframework.stereotype.Component
import pl.jwizard.jwl.i18n.I18nBean
import pl.jwizard.jwl.util.logger

/**
 * An exception advisor that handles unspecified (generic) exceptions by logging the error and returning a 500 Internal
 * Server Error response.
 *
 * This advisor is intended to catch any exceptions that are not specifically handled by other exception advisors.
 *
 * @param i18nBean An instance of [I18nBean] used for internationalization (i18n).
 * @author Miłosz Gilga
 */
@Component
class UnspecifiedExceptionAdvisor(i18nBean: I18nBean) : ExceptionsAdvisorBase<Exception>(i18nBean) {

	companion object {
		private val log = logger<UnspecifiedExceptionAdvisor>()
	}

	override val clazz = Exception::class

	/**
	 * This method is called when an exception is caught. It logs the exception and throws a 500 Internal Server Error
	 * response.
	 *
	 * @param ex The exception that was thrown and caught.
	 * @param ctx The [Context] object, which represents the HTTP request and response.
	 * @return An [ExceptionResponse] object, which in this case is an internal server error response.
	 */
	override fun advise(ex: Exception, ctx: Context): ExceptionResponse {
		log.error("Unexpected exception occurred. Cause: {}.", ex.message)
		throw InternalServerErrorResponse()
	}
}
