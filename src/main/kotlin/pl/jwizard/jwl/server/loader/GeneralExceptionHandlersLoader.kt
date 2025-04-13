package pl.jwizard.jwl.server.loader

import io.javalin.Javalin
import io.javalin.http.HttpStatus
import pl.jwizard.jwl.server.BaseServerAttribute
import pl.jwizard.jwl.server.exception.ExceptionData
import pl.jwizard.jwl.server.getAttribute

internal class GeneralExceptionHandlersLoader(javalin: Javalin) :
	ComponentsLoader<HttpStatus>(javalin) {
	override val name = "status code exception handler"

	override val extractor = {
		HttpStatus.entries.filter(HttpStatus::isError)
	}

	override fun loadComponent(javalin: Javalin, component: HttpStatus): List<String> {
		val exceptionName = component.name.uppercase().replace(" ", "_")
		javalin.error(component.code) {
			// send response only if exception handler not found
			val exceptionPassed = it.getAttribute<Boolean>(BaseServerAttribute.EXCEPTION_PASSED)
			if (exceptionPassed == true) {
				return@error
			}
			it.status(component.code).json(ExceptionData(exceptionName))
		}
		return listOf("status code: ${component.code} (${exceptionName})")
	}
}
