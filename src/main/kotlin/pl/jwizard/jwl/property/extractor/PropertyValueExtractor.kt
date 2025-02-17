package pl.jwizard.jwl.property.extractor

import pl.jwizard.jwl.property.PropertyNotFoundException
import pl.jwizard.jwl.property.PropertySourceData
import kotlin.reflect.KClass

internal abstract class PropertyValueExtractor(clazz: KClass<*>) : PropertySourceData(clazz) {
	companion object {
		private const val SEPARATOR = ":"
	}

	override fun getProperty(name: String): Any? {
		val qualifier = "${extractionKey}$SEPARATOR"
		if (!name.startsWith(qualifier)) {
			return null
		}
		val keyFragments = name.substring(qualifier.length).split(SEPARATOR)
		val key = keyFragments[0]
		val parsedProperty = super.getProperty(key)
		if (parsedProperty == null) {
			if (keyFragments.size != 2) { // has not defaulted value
				throw PropertyNotFoundException(this::class, name)
			}
			return keyFragments[1]
		}
		return parsedProperty
	}

	protected abstract val extractionKey: String
}
