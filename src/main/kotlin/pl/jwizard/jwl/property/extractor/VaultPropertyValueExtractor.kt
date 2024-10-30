/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.property.extractor

import pl.jwizard.jwl.property.BaseEnvironment
import pl.jwizard.jwl.property.vault.VaultClient

/**
 * Extractor for property values from HashiCorp Vault.
 *
 * @property environment The environment configuration used to initialize the Vault client.
 * @property vaultKvDefaultContext The default context path in Vault for key-value secrets.
 * @property vaultKvApplicationName The application-specific path in Vault for key-value secrets.
 * @author Miłosz Gilga
 * @see PropertyValueExtractor
 */
class VaultPropertyValueExtractor(
	private val environment: BaseEnvironment,
	private val vaultKvDefaultContext: String,
	private val vaultKvApplicationName: String,
) : PropertyValueExtractor(VaultPropertyValueExtractor::class) {

	/**
	 * A Vault client instance used to interact with the HashiCorp Vault.
	 */
	private val vaultClient = VaultClient(environment)

	init {
		vaultClient.init()
	}

	/**
	 * Retrieves properties from Vault and combines them into a single map.
	 *
	 * This method reads secrets from two paths in Vault: one specified by [vaultKvDefaultContext] and another by
	 * [vaultKvApplicationName]. The results from both paths are merged into a single map of properties.
	 *
	 * @return A map of properties where keys are property names and values are property values.
	 */
	override fun setProperties() =
		vaultClient.readKvSecrets(vaultKvDefaultContext) + vaultClient.readKvSecrets(vaultKvApplicationName)

	override val extractionKey = "vault"
}
