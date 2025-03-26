package pl.jwizard.jwl.vault

import org.springframework.vault.authentication.ClientAuthentication
import org.springframework.vault.authentication.UsernamePasswordAuthentication
import org.springframework.vault.authentication.UsernamePasswordAuthenticationOptions
import org.springframework.web.client.RestTemplate
import pl.jwizard.jwl.property.AppBaseProperty
import pl.jwizard.jwl.property.BaseEnvironment

internal class VaultUserpassAuthenticator : VaultAuthenticator {
	override val canRevokeAccess = true

	override fun authenticate(
		environment: BaseEnvironment,
		restTemplate: RestTemplate,
	): ClientAuthentication {
		val options = UsernamePasswordAuthenticationOptions.builder()
			.username(environment.getProperty<String>(AppBaseProperty.VAULT_USERNAME))
			.password(environment.getProperty<String>(AppBaseProperty.VAULT_PASSWORD))
			.build()
		return UsernamePasswordAuthentication(options, restTemplate)
	}
}
