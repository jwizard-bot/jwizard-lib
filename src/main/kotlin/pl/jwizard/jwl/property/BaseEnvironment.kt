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
 * Vault secrets. This class provides mechanisms for accessing single and multi-value properties and supports custom
 * property sources through the [setAdditionalSourceLoaders] method.
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
	lateinit var propertiesEnv: PropertiesEnvironment
		private set

	/**
	 * Initializes the property sources and loads properties from YAML files, environment variables, Vault, etc.
	 *
	 * This method is annotated with [PostConstruct] to ensure it is executed after the dependent bean(s) from
	 * [setAdditionalSourceLoaders] has been created.
	 */
	@PostConstruct
	internal fun initProperties() {
		propertiesEnv = PropertiesEnvironment(environment.propertySources)
		propertiesEnv.createResolver()

		val runtimeProfiles = getListProperty<String>(AppBaseListProperty.RUNTIME_PROFILES)
		propertiesEnv.addSource(YamlPropertySourceLoader(runtimeProfiles))
		log.info("Loaded runtime profiles: {}", runtimeProfiles)

		val envFileEnabled = getProperty<Boolean>(AppBaseProperty.ENV_ENABLED)
		propertiesEnv.addSource(EnvPropertyValueExtractor(envFileEnabled))

		propertiesEnv.addSource(
			VaultPropertyValueExtractor(
				vaultServerUri = getProperty(AppBaseProperty.VAULT_URL),
				vaultToken = getProperty(AppBaseProperty.VAULT_TOKEN),
				vaultKvBackend = getProperty(AppBaseProperty.VAULT_KV_BACKEND),
				vaultKvDefaultContext = getProperty(AppBaseProperty.VAULT_KV_DEFAULT_CONTEXT),
				vaultKvApplicationName = getProperty(AppBaseProperty.VAULT_KV_APPLICATION_NAME),
			)
		)
		setAdditionalSourceLoaders().forEach { propertiesEnv.addSource(it) }
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

	/**
	 * Provides additional property source loaders, allowing subclasses to add custom sources.
	 *
	 * Override this method to return a list of additional [PropertySourceData] loaders, which will be incorporated into
	 * the property environment.
	 *
	 * @return A list of [PropertySourceData] loaders to add additional sources to the environment.
	 */
	open fun setAdditionalSourceLoaders(): List<PropertySourceData> = emptyList()
}
