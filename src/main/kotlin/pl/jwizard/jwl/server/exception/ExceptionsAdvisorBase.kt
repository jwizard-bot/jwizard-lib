package pl.jwizard.jwl.server.exception

import io.javalin.http.Context
import pl.jwizard.jwl.i18n.I18n
import pl.jwizard.jwl.server.attribute.CommonServerAttribute
import pl.jwizard.jwl.server.getAttribute
import kotlin.reflect.KClass

abstract class ExceptionsAdvisorBase<T : Exception>(protected val i18n: I18n) {
	fun executeStatement(ex: T, ctx: Context) {
		val (status, i18nLocaleSource, args, details) = advise(ex, ctx)
		val locale = ctx.getAttribute<String>(CommonServerAttribute.I18N_LOCALE)
		val response = HttpErrorResponseDto(
			status = status.code,
			message = i18n.t(i18nLocaleSource, locale, args),
			details,
		)
		ctx.json(response).status(status)
	}

	abstract val clazz: KClass<T>

	protected abstract fun advise(ex: T, ctx: Context): ExceptionResponse
}
