package pl.jwizard.jwl.server.exception

import io.javalin.http.Context
import pl.jwizard.jwl.server.BaseServerAttribute
import pl.jwizard.jwl.server.setAttribute

class HttpExceptionAdvisor : ExceptionAdvisor<HttpException> {
	override val clazz = HttpException::class

	override fun executeStatement(ex: HttpException, ctx: Context) {
		ex.message?.let {
			ctx.json(ExceptionData(it))
			ctx.setAttribute(BaseServerAttribute.EXCEPTION_PASSED, true)
		}
		ctx.status(ex.status)
	}
}
