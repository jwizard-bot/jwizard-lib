package pl.jwizard.jwl.server.exception

import io.javalin.http.Context
import io.javalin.http.InternalServerErrorResponse
import pl.jwizard.jwl.i18n.I18nBean
import pl.jwizard.jwl.ioc.stereotype.SingletonComponent
import pl.jwizard.jwl.util.logger

@SingletonComponent
class UnspecifiedExceptionAdvisor(
	i18nBean: I18nBean,
) : ExceptionsAdvisorBase<Exception>(i18nBean) {
	companion object {
		private val log = logger<UnspecifiedExceptionAdvisor>()
	}

	override val clazz = Exception::class

	override fun advise(ex: Exception, ctx: Context): ExceptionResponse {
		log.error("Unexpected exception occurred. Cause: {}.", ex.message)
		throw InternalServerErrorResponse()
	}
}
