/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.property.vault

import io.github.jopenlibs.vault.VaultConfig
import pl.jwizard.jwl.property.AppBaseProperty
import pl.jwizard.jwl.property.BaseEnvironment

/**
 * Authenticator for Vault using a token-based authentication mechanism.
 *
 * This implementation of [VaultAuthenticator] retrieves an authentication token from the environment and uses it to
 * authenticate with Vault.
 *
 * @autor Miłosz Gilga
 * @see VaultAuthenticator
 */
class VaultTokenAuthenticator : VaultAuthenticator {

	/**
	 * Retrieves the authentication token from the environment and applies it to the Vault configuration.
	 *
	 * @param config Vault configuration object to be updated with the authentication token.
	 * @param environment The environment settings providing the token for Vault access.
	 * @return The authentication token as a [String] from the environment.
	 */
	override fun authenticate(config: VaultConfig, environment: BaseEnvironment) =
		environment.getProperty<String>(AppBaseProperty.VAULT_TOKEN)
}
