package pl.jwizard.jwl.property

import org.springframework.core.env.MutablePropertySources
import org.springframework.core.env.PropertySourcesPropertyResolver
import pl.jwizard.jwl.property.extractor.PropertyValueExtractor

class PropertiesEnvironment(private val propertySources: MutablePropertySources) {
	var size = 0
		private set

	val propertySourceNames = mutableListOf<String>()

	var resolver = PropertySourcesPropertyResolver(propertySources)
		private set

	fun addSource(sourceData: PropertySourceData) {
		sourceData.loadProperties()
		propertySources.addLast(sourceData)
		propertySourceNames.add(sourceData.javaClass.simpleName)
		if (sourceData !is PropertyValueExtractor) {
			size += sourceData.source.size
		}
		resolver = createResolver()
	}

	fun createResolver() = PropertySourcesPropertyResolver(propertySources)
}
