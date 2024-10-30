/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.property.vault

/**
 * Enum representing supported authentication types for HashiCorp Vault.
 *
 * Each type provides an ID and a specific authenticator instance for use in the Vault environment.
 *
 * @property id The identifier used to specify the authentication type.
 * @property authenticator The authenticator implementation corresponding to the authentication type.
 * @author Miłosz Gilga
 */
enum class VaultAuthenticationType(
	val id: String,
	val authenticator: VaultAuthenticator,
) {

	/**
	 * Token-based authentication type.
	 */
	TOKEN("token", VaultTokenAuthenticator()),

	/**
	 * Username and password-based authentication type.
	 */
	USERPASS("userpass", VaultUserpassAuthenticator()),
	;
}
