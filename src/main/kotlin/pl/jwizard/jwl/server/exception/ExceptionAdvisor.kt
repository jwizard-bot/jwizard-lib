package pl.jwizard.jwl.server.exception

import io.javalin.http.Context
import kotlin.reflect.KClass

interface ExceptionAdvisor<T : Exception> {
	val clazz: KClass<T>

	fun executeStatement(ex: T, ctx: Context)
}
