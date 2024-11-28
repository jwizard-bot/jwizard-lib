/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.property.vault

import io.github.jopenlibs.vault.Vault
import io.github.jopenlibs.vault.VaultConfig
import io.github.jopenlibs.vault.VaultException
import io.github.jopenlibs.vault.json.JsonObject
import pl.jwizard.jwl.IrreparableException
import pl.jwizard.jwl.property.AppBaseProperty
import pl.jwizard.jwl.property.BaseEnvironment
import pl.jwizard.jwl.util.logger
import java.util.*

/**
 * Client for managing connections and interactions with HashiCorp Vault.
 *
 * This client handles the configuration and initialization of Vault connections, providing methods to authenticate and
 * retrieve secrets from specified KV stores within Vault.
 *
 * @property environment The environment configuration used to initialize and configure the Vault client.
 * @autor Miłosz Gilga
 * @see VaultAuthenticationType
 * @see VaultAuthenticator
 */
class VaultClient(private val environment: BaseEnvironment) {

	companion object {
		private val log = logger<VaultClient>()
	}

	/**
	 * Builder for configuring the Vault client connection.
	 */
	private val configBuilder = VaultConfig()

	/**
	 * Vault client instance for interacting with Vault APIs.
	 */
	private lateinit var client: Vault

	init {
		configBuilder.address(environment.getProperty(AppBaseProperty.VAULT_URL))
		configBuilder.engineVersion(environment.getProperty<Int>(AppBaseProperty.VAULT_KV_VERSION))
	}

	/**
	 * Initializes the Vault client with appropriate authentication.
	 *
	 * Determines the authentication type based on [environment] settings and uses it to authenticate with Vault. After
	 * successful authentication, the Vault client instance is initialized with the obtained token.
	 *
	 * @throws IrreparableException if the authentication type is not supported or Vault configuration fails.
	 */
	fun init() {
		val rawType = environment.getProperty<String>(AppBaseProperty.VAULT_AUTHENTICATION_TYPE)
		val authenticationType = try {
			VaultAuthenticationType.valueOf(rawType)
		} catch (_: Exception) {
			throw IrreparableException(this::class, "Followed authentication type: %s is not supported.", rawType)
		}
		try {
			val config = configBuilder.build()
			val token = authenticationType.authenticator.authenticate(config, environment)
			client = Vault(config.token(token))
			log.info("Init vault client: {} with authentication type: {}.", configBuilder.address, authenticationType)
		} catch (ex: VaultException) {
			throw IrreparableException(this::class, "Unable to configure Vault. Cause: %s.", ex.message)
		}
	}

	/**
	 * Reads and retrieves secrets from a specified KV store in Vault.
	 *
	 * This method accesses the KV store identified by [kvStore] and reads all key-value pairs, returning them as
	 * a [Properties] object.
	 *
	 * @param kvStore The name of the KV store path to retrieve secrets from.
	 * @return A [Properties] object containing all retrieved secrets from the specified KV store.
	 */
	fun readKvSecrets(kvStore: String): Properties {
		val kvBackend = environment.getProperty<String>(AppBaseProperty.VAULT_KV_BACKEND)
		val properties = Properties()

		val qualifiedKvStorePath = "$kvBackend/$kvStore"
		val response = client.logical().read(qualifiedKvStorePath)
		response.data.forEach { properties[it.key] = it.value }
		log.info("Load: {} secrets from: {} KV store.", response.data?.size, qualifiedKvStorePath)

		return properties
	}

	/**
	 * Reads secrets from a Vault KV (Key-Value) store and returns them as a JSON object.
	 *
	 * @param kvStore The path of the KV store within the Vault backend to fetch secrets from.
	 * @return A [JsonObject] containing the secrets retrieved from the specified KV store.
	 * @throws VaultException If the Vault client fails to read the specified path or if the data is invalid.
	 */
	fun readKvSecretsAsJson(kvStore: String): JsonObject {
		val kvBackend = environment.getProperty<String>(AppBaseProperty.VAULT_KV_BACKEND)
		val qualifiedKvStorePath = "$kvBackend/$kvStore"
		return client.logical().read(qualifiedKvStorePath).dataObject
	}
}
