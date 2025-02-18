package pl.jwizard.jwl.vault.kvgroup

import pl.jwizard.jwl.property.PropertyNotFoundException
import pl.jwizard.jwl.util.castToValue
import java.util.*

class VaultKvGroupProperties<U : VaultKvGroupPropertySource>(
	incomingProperties: Properties,
) {
	var properties = incomingProperties.entries
		.associate { it.key.toString() to it.value.toString() }
		private set

	inline fun <reified T> get(kvGroupProperty: U): T {
		val rawProperty = properties[kvGroupProperty.key]
			?: throw PropertyNotFoundException(this::class, kvGroupProperty.key)
		return castToValue(rawProperty, kvGroupProperty.type) as T
	}
}
