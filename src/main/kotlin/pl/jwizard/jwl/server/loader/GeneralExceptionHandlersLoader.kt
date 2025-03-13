package pl.jwizard.jwl.server.loader

import io.javalin.Javalin

internal class GeneralExceptionHandlersLoader(javalin: Javalin) : ComponentsLoader<Int>(javalin) {
	override val name = "status code exception handler"

	override val extractor = {
		listOf(400, 401, 403, 404, 500)
	}

	override fun loadComponent(javalin: Javalin, component: Int): List<String> {
		javalin.error(component) { it.status(component).result("") }
		return listOf("status code: $component")
	}
}
