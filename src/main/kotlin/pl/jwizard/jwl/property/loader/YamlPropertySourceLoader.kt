package pl.jwizard.jwl.property.loader

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean
import org.springframework.core.io.ClassPathResource
import pl.jwizard.jwl.ioc.IoCKtContextFactory
import pl.jwizard.jwl.property.PropertySourceData
import pl.jwizard.jwl.util.logger

internal class YamlPropertySourceLoader(
	private val runtimeProfiles: List<String>,
) : PropertySourceData(YamlPropertySourceLoader::class) {
	companion object {
		private val log = logger<YamlPropertySourceLoader>()

		private const val DEFAULT_YAML_PREFIX = "config/application"
		private const val LIBRARY_YAML_PREFIX = "/config-lib/library"
		private val YAML_EXTENSIONS = arrayOf("yaml", "yml")
	}

	override fun setProperties(): Map<Any, Any> {
		val yamlPropertiesFactoryBean = YamlPropertiesFactoryBean()

		val appProperties =
			loadDefaultAndRuntimeDependentProperties(DEFAULT_YAML_PREFIX) { ClassPathResource(it) }
		val libProperties = loadDefaultAndRuntimeDependentProperties(LIBRARY_YAML_PREFIX) {
			ClassPathResource(it, IoCKtContextFactory::class.java)
		}
		val fileContents = appProperties + libProperties
		yamlPropertiesFactoryBean.setResources(*fileContents.toTypedArray())
		log.info("Load YAML configuration files: {}.", fileContents.map { it.filename })

		val properties = yamlPropertiesFactoryBean.getObject()
		return properties?.map { it.key to it.value }?.toMap() ?: emptyMap()
	}

	private fun loadDefaultAndRuntimeDependentProperties(
		prefix: String,
		resourceCallback: (String) -> ClassPathResource,
	): List<ClassPathResource> {
		val fileContents = mutableListOf<ClassPathResource>()

		val defaultProperties = getExistingYamlFile { resourceCallback("$prefix.$it") }
		val runtimeProperties = runtimeProfiles.map { profile ->
			getExistingYamlFile { resourceCallback("$prefix.$profile.$it") }
		}
		defaultProperties?.let { fileContents.add(it) }
		runtimeProperties.forEach { propertiesMode -> propertiesMode?.let { fileContents.add(it) } }

		return fileContents
	}

	private fun getExistingYamlFile(
		resourceCallback: (String) -> ClassPathResource,
	) = YAML_EXTENSIONS
		.map { resourceCallback(it) }
		.firstOrNull { it.exists() }
}
