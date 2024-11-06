/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.server.exception

import io.javalin.http.Context
import pl.jwizard.jwl.i18n.I18nBean
import pl.jwizard.jwl.server.attribute.CommonServerAttribute
import pl.jwizard.jwl.server.getAttribute
import kotlin.reflect.KClass

/**
 * Base class for exception advisors that handle specific exception types and provide error response translations
 * using the provided [I18nBean].
 *
 * This class serves as the base for advising specific exceptions and generating appropriate error responses in the
 * context of the Javalin HTTP server. It allows for the exception to be handled in a uniform way while providing
 * translated messages and correct HTTP status codes.
 *
 * @param T The type of exception this advisor is handling.
 * @property i18nBean A bean that handles internationalization (I18n) to translate error messages.
 * @author Miłosz Gilga
 */
abstract class ExceptionsAdvisorBase<T : Exception>(private val i18nBean: I18nBean) {

	/**
	 * Executes the exception handling process by advising the exception and generating an error response with a
	 * translated message and appropriate status code.
	 *
	 * This method will fetch the exception's status code and its translated message, then create a response DTO
	 * containing these details. It will send the response to the client with the correct HTTP status.
	 *
	 * @param ex The exception that was thrown and needs to be handled.
	 * @param ctx The context of the HTTP request, used to retrieve information like the locale.
	 */
	fun executeStatement(ex: T, ctx: Context) {
		val (status, i18nLocaleSource, args) = advise(ex, ctx)
		val locale = ctx.getAttribute<String>(CommonServerAttribute.I18N_LOCALE)
		val response = HttpErrorResponseDto(
			status = status.code,
			message = i18nBean.t(i18nLocaleSource, locale, args),
		)
		ctx.json(response).status(status)
	}

	/**
	 * The class type of the exception being handled.
	 */
	abstract val clazz: KClass<T>

	/**
	 * Provides the advice for a specific exception type, determining the status code and the translation source for
	 * the error message.
	 *
	 * This method must be implemented by subclasses to define how specific exception types are handled.
	 *
	 * @param ex The exception being handled.
	 * @param ctx The context of the HTTP request.
	 * @return An [ExceptionResponse] containing the status code, the translation source, and any arguments for the
	 *         message.
	 */
	protected abstract fun advise(ex: T, ctx: Context): ExceptionResponse
}
