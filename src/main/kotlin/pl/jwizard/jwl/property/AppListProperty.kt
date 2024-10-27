/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.property

import kotlin.reflect.KClass

/**
 * Represents a property that holds a list of elements, providing information about the element type and an optional
 * separator for parsing list values from a string.
 *
 * Classes implementing this interface should specify the type of elements in the list and, if applicable, define a
 * separator character or string used to delimit items in a serialized representation.
 *
 * @author Miłosz Gilga
 */
interface AppListProperty : Property {

	/**
	 * The type of elements in the list represented by this property.
	 */
	val listElementsType: KClass<*>

	/**
	 * Optional separator used to split the list elements in a string representation.
	 */
	val separator: String?
}
