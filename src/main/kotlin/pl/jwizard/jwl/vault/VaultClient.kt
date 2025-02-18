package pl.jwizard.jwl.vault

import io.github.jopenlibs.vault.Vault
import io.github.jopenlibs.vault.VaultConfig
import io.github.jopenlibs.vault.VaultException
import pl.jwizard.jwl.IrreparableException
import pl.jwizard.jwl.property.AppBaseProperty
import pl.jwizard.jwl.property.BaseEnvironment
import pl.jwizard.jwl.util.logger
import pl.jwizard.jwl.vault.kvgroup.VaultKvGroupProperties
import pl.jwizard.jwl.vault.kvgroup.VaultKvGroupPropertySource
import java.util.*

class VaultClient(private val environment: BaseEnvironment) {
	companion object {
		private val log = logger<VaultClient>()
	}

	private val rawType = environment.getProperty<String>(AppBaseProperty.VAULT_AUTHENTICATION_TYPE)

	private val configBuilder = VaultConfig()
	private var isAuthenticated = false
	private lateinit var client: Vault
	private val authenticationType: VaultAuthenticationType

	init {
		configBuilder.address(environment.getProperty(AppBaseProperty.VAULT_URL))
		configBuilder.engineVersion(environment.getProperty<Int>(AppBaseProperty.VAULT_KV_VERSION))
		authenticationType = determinateAuthenticationType()
	}

	fun initOnce() {
		if (isAuthenticated) {
			return // ignore init when vault is already authenticated
		}
		val config = configBuilder.build()
		try {
			val token = authenticationType.authenticator.authenticate(config, environment)
			client = Vault(config.token(token))
			log.info(
				"Authenticate to vault client: {} with authentication type: {}.",
				configBuilder.address,
				authenticationType,
			)
			isAuthenticated = true
		} catch (ex: VaultException) {
			throw IrreparableException(
				this::class,
				"Unable to authenticate Vault. Cause: %s.",
				ex.message,
			)
		}
	}

	// run after grab all properties and save in-memory
	fun revoke() {
		val actualRevoked = authenticationType.authenticator.revokeAccess(client)
		if (actualRevoked) {
			log.info("Revoked access to vault storage.")
		}
		isAuthenticated = false
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

	fun <U, T : VaultKvGroupPropertySource> readKvGroupPropertySource(
		kvPath: String = "",
		patternFilter: Regex? = null,
		keyExtractor: (kvGroupName: String) -> U,
		immediatelyRevoke: Boolean = true,
	): Map<U, VaultKvGroupProperties<T>> {
		val kvGroupNames = readKvPaths(kvPath, patternFilter)
		val kvGroupProperties = kvGroupNames.associate {
			keyExtractor(it) to VaultKvGroupProperties<T>(readKvSecrets("$kvPath/$it"))
		}
		if (immediatelyRevoke) {
			revoke()
		}
		return kvGroupProperties
	}

	private fun determinateAuthenticationType() = try {
		VaultAuthenticationType.valueOf(rawType)
	} catch (_: Exception) {
		throw IrreparableException(
			this::class,
			"Followed authentication type: %s is not supported.",
			rawType,
		)
	}
}
