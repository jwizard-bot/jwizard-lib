package pl.jwizard.jwl.property

import org.springframework.core.env.PropertySource
import java.util.*
import kotlin.reflect.KClass

abstract class PropertySourceData(
	clazz: KClass<*>,
) : PropertySource<Properties>(clazz.java.simpleName, Properties()) {
	fun loadProperties() {
		val propertiesAsMap = this.setProperties()
		propertiesAsMap.forEach { source[it.key] = it.value }
	}

	override fun getProperty(name: String): Any? = source[name]

	protected abstract fun setProperties(): Map<Any, Any>
}
