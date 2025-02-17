package pl.jwizard.jwl.property.extractor

import pl.jwizard.jwl.property.BaseEnvironment
import pl.jwizard.jwl.vault.VaultClient

internal class VaultPropertyValueExtractor(
	environment: BaseEnvironment,
	private val vaultKvDefaultContext: String,
	private val vaultKvApplicationNames: List<String>,
) : PropertyValueExtractor(VaultPropertyValueExtractor::class) {
	private val vaultClient = VaultClient(environment)

	init {
		vaultClient.init()
	}

	override fun setProperties(): Map<Any, Any> {
		val allSecrets = vaultClient.readKvSecrets(vaultKvDefaultContext)
		vaultKvApplicationNames.forEach { allSecrets += vaultClient.readKvSecrets(it) }
		return allSecrets
	}

	override val extractionKey = "vault"
}
