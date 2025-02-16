package pl.jwizard.jwl.property.vault

enum class VaultAuthenticationType(
	val id: String,
	val authenticator: VaultAuthenticator,
) {
	TOKEN("token", VaultTokenAuthenticator()),
	USERPASS("userpass", VaultUserpassAuthenticator()),
	;
}
