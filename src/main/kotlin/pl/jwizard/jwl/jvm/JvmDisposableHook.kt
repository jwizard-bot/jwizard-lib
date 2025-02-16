package pl.jwizard.jwl.jvm

import pl.jwizard.jwl.util.logger
import kotlin.reflect.jvm.jvmName

class JvmDisposableHook(private val jvmDisposable: JvmDisposable) {
	companion object {
		private val log = logger<JvmDisposableHook>()
	}

	private val runtime = Runtime.getRuntime()

	fun initHook() {
		val thread = Thread { jvmDisposable.cleanBeforeDisposeJvm() }
		runtime.addShutdownHook(thread)
		log.info("Init JVM disposable hook for: {}.", jvmDisposable::class.jvmName)
	}
}
