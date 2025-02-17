package pl.jwizard.jwl.vault

internal enum class VaultAuthenticationType(
	val id: String,
	val authenticator: VaultAuthenticator,
) {
	TOKEN("token", VaultTokenAuthenticator()),
	USERPASS("userpass", VaultUserpassAuthenticator()),
	;
}
