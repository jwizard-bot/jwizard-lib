package pl.jwizard.jwl.server.exception

import io.javalin.http.Context
import io.javalin.http.InternalServerErrorResponse
import pl.jwizard.jwl.util.logger

class UnspecifiedExceptionAdvisor : ExceptionAdvisor<Exception> {
	companion object {
		private val log = logger<UnspecifiedExceptionAdvisor>()
	}

	override val clazz = Exception::class

	override fun executeStatement(ex: Exception, ctx: Context) {
		log.error("Unexpected exception occurred. Cause: {}.", ex.message)
		throw InternalServerErrorResponse()
	}
}
