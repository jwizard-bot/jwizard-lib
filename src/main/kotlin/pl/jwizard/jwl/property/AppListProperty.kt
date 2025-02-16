package pl.jwizard.jwl.property

import kotlin.reflect.KClass

// implement this interface on enum with multiple-value application properties
interface AppListProperty : Property {
	val listElementsType: KClass<*>

	// values separator (ex. comma), used when value is writable as string (ex. in command line
	// parameters)
	val separator: String?
}
