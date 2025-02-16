package pl.jwizard.jwl.property

import kotlin.reflect.KClass

// implement this interface on enum with single-value application properties
interface AppProperty : Property {
	val type: KClass<*>
}
