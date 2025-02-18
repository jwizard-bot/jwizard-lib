package pl.jwizard.jwl.vault

import io.github.jopenlibs.vault.Vault
import io.github.jopenlibs.vault.VaultConfig
import pl.jwizard.jwl.property.BaseEnvironment

internal interface VaultAuthenticator {
	fun authenticate(config: VaultConfig, environment: BaseEnvironment): String

	fun revokeAccess(vault: Vault): Boolean
}
