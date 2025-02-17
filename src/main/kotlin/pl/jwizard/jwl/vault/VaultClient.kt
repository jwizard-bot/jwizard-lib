package pl.jwizard.jwl.vault

import io.github.jopenlibs.vault.Vault
import io.github.jopenlibs.vault.VaultConfig
import io.github.jopenlibs.vault.VaultException
import io.github.jopenlibs.vault.json.JsonObject
import pl.jwizard.jwl.IrreparableException
import pl.jwizard.jwl.property.AppBaseProperty
import pl.jwizard.jwl.property.BaseEnvironment
import pl.jwizard.jwl.util.logger
import java.util.*

class VaultClient(private val environment: BaseEnvironment) {
	companion object {
		private val log = logger<VaultClient>()
	}

	private val configBuilder = VaultConfig()
	private lateinit var client: Vault

	init {
		configBuilder.address(environment.getProperty(AppBaseProperty.VAULT_URL))
		configBuilder.engineVersion(environment.getProperty<Int>(AppBaseProperty.VAULT_KV_VERSION))
	}

	fun init() {
		val rawType = environment.getProperty<String>(AppBaseProperty.VAULT_AUTHENTICATION_TYPE)
		val authenticationType = try {
			VaultAuthenticationType.valueOf(rawType)
		} catch (_: Exception) {
			throw IrreparableException(
				this::class,
				"Followed authentication type: %s is not supported.",
				rawType,
			)
		}
		try {
			val config = configBuilder.build()
			val token = authenticationType.authenticator.authenticate(config, environment)
			client = Vault(config.token(token))
			log.info(
				"Authenticate to vault client: {} with authentication type: {}.",
				configBuilder.address,
				authenticationType,
			)
		} catch (ex: VaultException) {
			throw IrreparableException(
				this::class,
				"Unable to authenticate Vault. Cause: %s.",
				ex.message,
			)
		}
	}

	fun readKvPaths(kvPath: String = "", patternFilter: Regex? = null): List<String> {
		val kvBackend = environment.getProperty<String>(AppBaseProperty.VAULT_KV_BACKEND)
		val qualifiedKvPath = if (kvPath != "") {
			"$kvBackend/$kvPath"
		} else {
			kvBackend
		}
		val response = client.logical().list(qualifiedKvPath)
		val keys = response.dataObject.get("keys")
		if (keys.isNull) {
			return listOf()
		}
		val allKeys = keys.asArray().map { it.asString() }
		return if (patternFilter != null) {
			allKeys.filter { it.matches(patternFilter) }
		} else {
			allKeys
		}
	}

	fun readKvSecrets(kvStore: String): Properties {
		val kvBackend = environment.getProperty<String>(AppBaseProperty.VAULT_KV_BACKEND)
		val properties = Properties()

		val qualifiedKvStorePath = "$kvBackend/$kvStore"
		val response = client.logical().read(qualifiedKvStorePath)
		response.data.forEach { properties[it.key] = it.value }
		log.info("Load: {} secrets from: {} KV store.", response.data?.size, qualifiedKvStorePath)

		return properties
	}

	fun readKvSecretsAsJson(kvStore: String): JsonObject {
		val kvBackend = environment.getProperty<String>(AppBaseProperty.VAULT_KV_BACKEND)
		val qualifiedKvStorePath = "$kvBackend/$kvStore"
		return client.logical().read(qualifiedKvStorePath).dataObject
	}
}
