/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.property

import jakarta.annotation.PostConstruct
import org.springframework.core.env.StandardEnvironment
import pl.jwizard.jwl.SpringKtContextFactory
import pl.jwizard.jwl.property.extractor.EnvPropertyValueExtractor
import pl.jwizard.jwl.property.extractor.VaultPropertyValueExtractor
import pl.jwizard.jwl.property.loader.YamlPropertySourceLoader
import pl.jwizard.jwl.util.castToValue
import pl.jwizard.jwl.util.logger

/**
 * Manages environment-specific property sources, loading configurations from YAML files, environment variables, and
 * Vault secrets.
 *
 * @property springKtContextFactory Factory for creating the Spring context used to initialize properties.
 * @author Miłosz Gilga
 */
abstract class BaseEnvironment(private val springKtContextFactory: SpringKtContextFactory) {

	companion object {
		private val log = logger<BaseEnvironment>()
	}

	/**
	 * The [StandardEnvironment] instance used to manage and access property sources.
	 *
	 * This environment provides the context in which property sources are registered and managed. It allows access
	 * to properties from various sources such as YAML files, environment variables, and Vault secrets.
	 */
	private val environment = StandardEnvironment()

	/**
	 * An instance of [PropertiesEnvironment] that manages and resolves property sources.
	 */
	val propertiesEnv = PropertiesEnvironment(environment.propertySources)

	/**
	 * Initializes the environment properties by loading various property sources.
	 *
	 * This method is automatically called after the bean's construction. It prepares and adds multiple property sources,
	 * such as YAML files for runtime profiles, environment variables, and Vault secrets, to the application's property
	 * environment.
	 *
	 * - Loads and resolves runtime profiles specified by [AppBaseListProperty.RUNTIME_PROFILES].
	 * - Adds environment file properties if [AppBaseProperty.ENV_ENABLED] is set to true.
	 * - Retrieves and adds secrets from HashiCorp Vault, based on the configured context and application names.
	 *
	 * Logs the count of properties and the sources from which they were loaded.
	 */
	@PostConstruct
	internal fun postConstruct() {
		propertiesEnv.createResolver()

		val runtimeProfiles = getListProperty<String>(AppBaseListProperty.RUNTIME_PROFILES)
		propertiesEnv.addSource(YamlPropertySourceLoader(runtimeProfiles))
		log.info("Loaded runtime profiles: {}", runtimeProfiles)

		val envFileEnabled = getProperty<Boolean>(AppBaseProperty.ENV_ENABLED)
		propertiesEnv.addSource(EnvPropertyValueExtractor(envFileEnabled))

		propertiesEnv.addSource(
			VaultPropertyValueExtractor(
				environment = this,
				vaultKvDefaultContext = getProperty(AppBaseProperty.VAULT_KV_DEFAULT_CONTEXT),
				vaultKvApplicationName = getProperty(AppBaseProperty.VAULT_KV_APPLICATION_NAME),
			)
		)
		log.info("Load: {} properties from sources: {}.", propertiesEnv.size, propertiesEnv.propertySourceNames)
	}

	/**
	 * Retrieves a list of properties of type [T] from a multi-property source.
	 *
	 * The properties are retrieved from a source where the properties are indexed by a key suffix. If no properties are
	 * found, a [PropertyNotFoundException] is thrown.
	 *
	 * @param T The type of the property values in the list.
	 * @param appListProperty The multi-property definition containing the key and element type.
	 * @return A list of properties of type [T].
	 */
	inline fun <reified T> getListProperty(appListProperty: AppListProperty): List<T> {
		val elements = mutableListOf<String>()
		val resolver = propertiesEnv.resolver
		if (appListProperty.separator == null) {
			var listIndex = 0
			while (true) {
				val listElement = resolver.getProperty("${appListProperty.key}[${listIndex++}]")
					?: break
				elements.add(listElement)
			}
		} else {
			val rawValues = resolver.getProperty(appListProperty.key)
				?: throw PropertyNotFoundException(this::class, appListProperty.key)
			rawValues.split(appListProperty.separator!!).forEach { elements.add(it.trim()) }
		}
		if (elements.isEmpty()) {
			throw PropertyNotFoundException(this::class, appListProperty.key)
		}
		return elements.map { castToValue(it, appListProperty.listElementsType) }
	}

	/**
	 * Retrieves a single property of type [T] from a property source.
	 *
	 * The property is retrieved based on the provided [AppProperty] key. If the property is not found, a
	 * [PropertyNotFoundException] is thrown.
	 *
	 * @param T The type of the property value.
	 * @param appProperty The property definition containing the key and type.
	 * @return The property value of type [T].
	 * @throws PropertyNotFoundException If property with following key not exist.
	 */
	inline fun <reified T : Any> getProperty(appProperty: AppProperty): T {
		val rawValue = propertiesEnv.resolver.getProperty(appProperty.key)
			?: throw PropertyNotFoundException(this::class, appProperty.key)
		return castToValue(rawValue, appProperty.type)
	}
}
