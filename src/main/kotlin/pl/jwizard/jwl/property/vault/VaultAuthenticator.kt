package pl.jwizard.jwl.property.vault

import io.github.jopenlibs.vault.VaultConfig
import pl.jwizard.jwl.property.BaseEnvironment

interface VaultAuthenticator {
	fun authenticate(config: VaultConfig, environment: BaseEnvironment): String
}
