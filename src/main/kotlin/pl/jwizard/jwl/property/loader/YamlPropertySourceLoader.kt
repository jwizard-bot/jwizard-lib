/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.property.loader

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean
import org.springframework.core.io.ClassPathResource
import pl.jwizard.jwl.ioc.IoCKtContextFactory
import pl.jwizard.jwl.property.PropertySourceData
import pl.jwizard.jwl.util.logger

/**
 * Loader for YAML property sources.
 *
 * @property runtimeProfiles List of runtime modes to load specific YAML configuration files.
 * @author Miłosz Gilga
 * @see PropertySourceData
 */
class YamlPropertySourceLoader(
	private val runtimeProfiles: List<String>,
) : PropertySourceData(YamlPropertySourceLoader::class) {

	companion object {
		private val log = logger<YamlPropertySourceLoader>()

		/**
		 * Default prefix for YAML configuration files.
		 */
		private const val DEFAULT_YAML_PREFIX = "config/application"

		/**
		 * Prefix for library-specific YAML configuration files.
		 */
		private const val LIBRARY_YAML_PREFIX = "/config-lib/library"

		/**
		 * Available YAML file extensions.
		 */
		private val YAML_EXTENSIONS = arrayOf("yaml", "yml")
	}

	/**
	 * Loads and merges application and library-specific YAML configuration files based on the current runtime profiles.
	 * This method creates a map of properties from all valid YAML files matching the current runtime profiles.
	 *
	 * @return A map containing the merged properties from the loaded YAML files.
	 */
	override fun setProperties(): Map<Any, Any> {
		val yamlPropertiesFactoryBean = YamlPropertiesFactoryBean()

		val appProperties = loadDefaultAndRuntimeDependentProperties(DEFAULT_YAML_PREFIX) { ClassPathResource(it) }
		val libProperties = loadDefaultAndRuntimeDependentProperties(LIBRARY_YAML_PREFIX) {
			ClassPathResource(it, IoCKtContextFactory::class.java)
		}
		val fileContents = appProperties + libProperties
		yamlPropertiesFactoryBean.setResources(*fileContents.toTypedArray())
		log.info("Load YAML configuration files: {}.", fileContents.map { it.filename })

		val properties = yamlPropertiesFactoryBean.getObject()
		return properties?.map { it.key to it.value }?.toMap() ?: emptyMap()
	}

	/**
	 * Loads YAML configuration files based on the default and runtime-specific profiles.
	 *
	 * This method creates a list of [ClassPathResource] objects by attempting to load YAML files for both default and
	 * runtime-specific profiles.
	 *
	 * @param prefix The prefix for the YAML file paths.
	 * @param resourceCallback A callback function to generate [ClassPathResource] instances for each file path.
	 * @return A list of [ClassPathResource] objects representing valid YAML files for the current configuration.
	 */
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

	/**
	 * Retrieves the first existing YAML file that matches the specified file extensions.
	 *
	 * @param resourceCallback A callback function to generate [ClassPathResource] instances based on file extension.
	 * @return The first [ClassPathResource] that exists, or null if no matching file is found.
	 */
	private fun getExistingYamlFile(resourceCallback: (String) -> ClassPathResource) = YAML_EXTENSIONS
		.map { resourceCallback(it) }
		.firstOrNull { it.exists() }
}
