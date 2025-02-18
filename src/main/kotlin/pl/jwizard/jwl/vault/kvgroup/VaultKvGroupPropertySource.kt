package pl.jwizard.jwl.vault.kvgroup

import kotlin.reflect.KClass

interface VaultKvGroupPropertySource {
	val key: String
	val type: KClass<*>
}
