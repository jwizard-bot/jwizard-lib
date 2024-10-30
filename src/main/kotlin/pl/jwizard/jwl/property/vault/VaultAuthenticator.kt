/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.property.vault

import io.github.jopenlibs.vault.VaultConfig
import pl.jwizard.jwl.property.BaseEnvironment

/**
 * Interface for authenticating with HashiCorp Vault using various methods.
 *
 * Implementations of this interface provide specific strategies for authenticating to Vault, each tailored to a
 * different authentication method (ex. token, username/password).
 *
 * @author Miłosz Gilga
 * @see VaultAuthenticationType
 */
interface VaultAuthenticator {

	/**
	 * Authenticates to Vault using the provided configuration and environment details.
	 *
	 * This method establishes an authenticated session with Vault based on the supplied [config] and [environment]
	 * settings. Each implementation of [VaultAuthenticator] defines its own authentication logic to obtain the
	 * appropriate authentication token.
	 *
	 * @param config Vault configuration object containing connection settings.
	 * @param environment Environment details and configurations specific to the application.
	 * @return A token string for authenticated access to Vault.
	 */
	fun authenticate(config: VaultConfig, environment: BaseEnvironment): String
}
