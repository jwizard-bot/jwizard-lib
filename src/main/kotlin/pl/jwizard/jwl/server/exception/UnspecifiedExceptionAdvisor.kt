package pl.jwizard.jwl.server.exception

import io.javalin.http.Context
import io.javalin.http.InternalServerErrorResponse
import pl.jwizard.jwl.i18n.I18n
import pl.jwizard.jwl.util.logger

class UnspecifiedExceptionAdvisor(
	i18n: I18n,
) : ExceptionsAdvisorBase<Exception>(i18n) {
	companion object {
		private val log = logger<UnspecifiedExceptionAdvisor>()
	}

	override val clazz = Exception::class

	override fun advise(ex: Exception, ctx: Context): ExceptionResponse {
		log.error("Unexpected exception occurred. Cause: {}.", ex.message)
		throw InternalServerErrorResponse()
	}
}
