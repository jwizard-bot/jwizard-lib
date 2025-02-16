package pl.jwizard.jwl

import org.slf4j.LoggerFactory
import kotlin.reflect.KClass
import kotlin.reflect.jvm.jvmName
import kotlin.system.exitProcess

// this exception if thrown terminates the entire application
open class IrreparableException(
	private val clazz: KClass<*>,
	messageContent: String = "",
	vararg args: Any?,
) : RuntimeException(String.format(messageContent, *args)) {
	private val stacktraceCauses = mutableListOf<String>()

	init {
		val message = String.format(messageContent, *args)
		if (message.isNotEmpty()) {
			stacktraceCauses.add(message)
		}
	}

	constructor(throwable: Throwable) : this(
		throwable.cause?.javaClass?.kotlin ?: IrreparableException::class
	) {
		var cause: Throwable? = throwable.cause
		while (cause != null) {
			stacktraceCauses.add("[${clazz.jvmName} -> ${cause.javaClass.name}]: ${cause.message}")
			cause = cause.cause
		}
		if (stacktraceCauses.isEmpty() && !throwable.message.isNullOrBlank()) {
			throwable.message?.let { stacktraceCauses.add(it) }
		}
	}

	fun printLogStatement() {
		val log = LoggerFactory.getLogger(clazz.java)
		stacktraceCauses.forEach { log.error(it) }
	}

	fun killProcess() {
		exitProcess(1)
	}
}
