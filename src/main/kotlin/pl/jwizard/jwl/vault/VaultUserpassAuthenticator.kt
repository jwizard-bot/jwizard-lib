package pl.jwizard.jwl.vault

import io.github.jopenlibs.vault.Vault
import io.github.jopenlibs.vault.VaultConfig
import pl.jwizard.jwl.property.AppBaseProperty
import pl.jwizard.jwl.property.BaseEnvironment

internal class VaultUserpassAuthenticator : VaultAuthenticator {
	override fun authenticate(config: VaultConfig, environment: BaseEnvironment): String {
		val vault = Vault(config)
		val response = vault.auth().loginByUserPass(
			environment.getProperty<String>(AppBaseProperty.VAULT_USERNAME),
			environment.getProperty<String>(AppBaseProperty.VAULT_PASSWORD),
			VaultAuthenticationType.USERPASS.id,
		)
		return response.authClientToken
	}
}
