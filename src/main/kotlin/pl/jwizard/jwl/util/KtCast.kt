package pl.jwizard.jwl.util

import kotlin.reflect.KClass

inline fun <reified T : Any> castToValue(value: String, targetType: KClass<*>): T {
	return when (targetType) {
		Int::class -> value.toInt() as T
		Double::class -> value.toDouble() as T
		Boolean::class -> value.toBoolean() as T
		Long::class -> value.toLong() as T
		else -> value as T
	}
}
