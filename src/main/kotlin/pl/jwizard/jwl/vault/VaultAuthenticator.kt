package pl.jwizard.jwl.vault

import org.springframework.vault.authentication.ClientAuthentication
import org.springframework.web.client.RestTemplate
import pl.jwizard.jwl.property.BaseEnvironment

internal interface VaultAuthenticator {
	val canRevokeAccess: Boolean

	fun authenticate(environment: BaseEnvironment, restTemplate: RestTemplate): ClientAuthentication
}
