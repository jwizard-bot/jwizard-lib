/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.property

import kotlin.reflect.KClass

/**
 * Defines a basic application property with a specific type, extending the [Property] interface. This interface allows
 * for specifying the type of the property value, which defaults to [String] when not explicitly defined.
 *
 * Classes implementing this interface should specify the [type] property, which indicates the expected data type for
 * the property value, providing flexibility for various data types in configuration.
 *
 * @author Miłosz Gilga
 */
interface AppProperty : Property {

	/**
	 * The type of the property value. Defaults to [String] if not specified.
	 */
	val type: KClass<*>
}
