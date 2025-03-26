package pl.jwizard.jwl.vault

import org.springframework.vault.authentication.ClientAuthentication
import org.springframework.vault.authentication.TokenAuthentication
import org.springframework.web.client.RestTemplate
import pl.jwizard.jwl.property.AppBaseProperty
import pl.jwizard.jwl.property.BaseEnvironment

internal class VaultTokenAuthenticator : VaultAuthenticator {
	// ignore revoking access in dev environment (for root token)
	override val canRevokeAccess = false

	// ignore tokenUseCount variable for development root token (unlimited count of usages)
	override fun authenticate(
		environment: BaseEnvironment,
		restTemplate: RestTemplate,
	): ClientAuthentication {
		val token = environment.getProperty<String>(AppBaseProperty.VAULT_TOKEN)
		return TokenAuthentication(token)
	}
}
