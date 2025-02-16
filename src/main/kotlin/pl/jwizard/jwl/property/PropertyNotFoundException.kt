package pl.jwizard.jwl.property

import pl.jwizard.jwl.IrreparableException
import kotlin.reflect.KClass

class PropertyNotFoundException(
	clazz: KClass<*>,
	propertyKey: String,
) : IrreparableException(
	clazz = clazz,
	messageContent = "Selected property: \"%s\" not found or could not be accessed.",
	args = arrayOf(propertyKey),
)
