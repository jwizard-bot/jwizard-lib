/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.property.vault

import io.github.jopenlibs.vault.Vault
import io.github.jopenlibs.vault.VaultConfig
import pl.jwizard.jwl.property.AppBaseProperty
import pl.jwizard.jwl.property.BaseEnvironment

/**
 * Authenticator for Vault using username and password-based authentication.
 *
 * This implementation of [VaultAuthenticator] uses a username and password stored in the environment to authenticate
 * with Vault and obtain a client token.
 *
 * @autor Miłosz Gilga
 * @see VaultAuthenticator
 */
class VaultUserpassAuthenticator : VaultAuthenticator {

	/**
	 * Authenticates to Vault using username and password, returning the client token.
	 *
	 * This method leverages Vault's userpass authentication backend, where it logs in using credentials obtained from
	 * [environment] and returns an authentication token.
	 *
	 * @param config Vault configuration object used to initialize the Vault client.
	 * @param environment The environment settings containing Vault username and password credentials.
	 * @return The client authentication token as a [String].
	 */
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
