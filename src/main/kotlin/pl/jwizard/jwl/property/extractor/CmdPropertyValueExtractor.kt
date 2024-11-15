/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.property.extractor

/**
 * A class responsible for extracting properties from the system's command-line environment.
 *
 * This implementation retrieves properties using the `System.getProperties()` method and associates them with a
 * specific extraction key, which is "cmd".
 *
 * @author Miłosz Gilga
 * @see PropertyValueExtractor
 */
class CmdPropertyValueExtractor : PropertyValueExtractor(CmdPropertyValueExtractor::class) {

	/**
	 * Retrieves system properties as a map of key-value pairs.
	 *
	 * This method uses the `System.getProperties()` function to return all system properties. The returned map contains
	 * property names as keys and their respective values.
	 *
	 * @return A map of properties where keys are property names and values are property values.
	 */
	override fun setProperties(): Map<Any, Any> = System.getProperties()

	override val extractionKey = "cmd"
}
