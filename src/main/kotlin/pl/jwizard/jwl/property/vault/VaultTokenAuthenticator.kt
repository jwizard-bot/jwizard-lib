package pl.jwizard.jwl.property.vault

import io.github.jopenlibs.vault.VaultConfig
import pl.jwizard.jwl.property.AppBaseProperty
import pl.jwizard.jwl.property.BaseEnvironment

class VaultTokenAuthenticator : VaultAuthenticator {
	override fun authenticate(
		config: VaultConfig,
		environment: BaseEnvironment,
	) = environment.getProperty<String>(AppBaseProperty.VAULT_TOKEN)
}
