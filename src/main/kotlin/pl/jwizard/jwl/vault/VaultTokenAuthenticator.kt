package pl.jwizard.jwl.vault

import io.github.jopenlibs.vault.Vault
import io.github.jopenlibs.vault.VaultConfig
import pl.jwizard.jwl.property.AppBaseProperty
import pl.jwizard.jwl.property.BaseEnvironment

internal class VaultTokenAuthenticator : VaultAuthenticator {
	// ignore tokenUseCount variable for development root token (unlimited count of usages)
	override fun authenticate(
		config: VaultConfig,
		environment: BaseEnvironment,
	) = environment.getProperty<String>(AppBaseProperty.VAULT_TOKEN)

	// ignore revoking access in dev environment (for root token)
	override fun revokeAccess(vault: Vault) = false
}
