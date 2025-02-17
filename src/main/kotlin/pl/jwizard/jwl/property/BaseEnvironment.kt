package pl.jwizard.jwl.property

import jakarta.annotation.PostConstruct
import org.springframework.core.env.StandardEnvironment
import pl.jwizard.jwl.property.extractor.CmdPropertyValueExtractor
import pl.jwizard.jwl.property.extractor.EnvPropertyValueExtractor
import pl.jwizard.jwl.property.extractor.VaultPropertyValueExtractor
import pl.jwizard.jwl.property.loader.YamlPropertySourceLoader
import pl.jwizard.jwl.util.castToValue
import pl.jwizard.jwl.util.logger

open class BaseEnvironment {
	companion object {
		private val log = logger<BaseEnvironment>()
	}

	private val environment = StandardEnvironment()
	val propertiesEnv = PropertiesEnvironment(environment.propertySources)

	@PostConstruct
	internal fun postConstruct() {
		propertiesEnv.createResolver()

		val runtimeProfiles = getListProperty<String>(AppBaseListProperty.RUNTIME_PROFILES)
		propertiesEnv.addSource(CmdPropertyValueExtractor())
		propertiesEnv.addSource(YamlPropertySourceLoader(runtimeProfiles))
		log.info("Loaded runtime profiles: {}", runtimeProfiles)

		val envFileEnabled = getProperty<Boolean>(AppBaseProperty.ENV_ENABLED)
		propertiesEnv.addSource(EnvPropertyValueExtractor(envFileEnabled))

		propertiesEnv.addSource(
			VaultPropertyValueExtractor(
				environment = this,
				vaultKvDefaultContext = getProperty(AppBaseProperty.VAULT_KV_DEFAULT_CONTEXT),
				vaultKvApplicationNames = getListProperty(AppBaseListProperty.VAULT_KV_APPLICATION_NAMES),
			)
		)
		log.info(
			"Load: {} properties from sources: {}.",
			propertiesEnv.size,
			propertiesEnv.propertySourceNames,
		)
	}

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

	inline fun <reified T : Any> getProperty(appProperty: AppProperty): T {
		val rawValue = propertiesEnv.resolver.getProperty(appProperty.key)
			?: throw PropertyNotFoundException(this::class, appProperty.key)
		return castToValue(rawValue, appProperty.type)
	}
}
