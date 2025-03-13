package pl.jwizard.jwl.server.loader

import io.javalin.Javalin
import pl.jwizard.jwl.util.logger

internal abstract class ComponentsLoader<T : Any>(private val javalin: Javalin) {
	companion object {
		private val log = logger<ComponentsLoader<*>>()
	}

	fun initComponents() {
		val components = extractor()
		for (component in componentTransformation(components)) {
			val logStatementInfo = loadComponent(javalin, component)
			log.info(
				"Load [{}]: {} -> ({}).",
				name,
				component::class.simpleName,
				logStatementInfo.joinToString(),
			)
		}
	}

	// used for extract components ex. via reflection api or manually declared
	abstract val extractor: () -> List<T>

	// used in log statements
	protected abstract val name: String

	// additional components list transformation (filter, sort etc.)
	protected open fun componentTransformation(components: List<T>) = components

	// in return, set additional log statement info (joined with ",")
	protected abstract fun loadComponent(javalin: Javalin, component: T): List<String>
}
