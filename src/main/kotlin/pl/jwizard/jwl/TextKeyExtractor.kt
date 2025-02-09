/*
 * Copyright (c) 2025 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl

/**
 * Provides an interface for extracting a unique text key identifier.
 *
 * Implementations of this interface are intended to define a [textKey] property that returns a unique key associated
 * with a specific piece of text, message, or resource identifier. This key can be used for localization, configuration,
 * or any context where a text-based identifier is required.
 *
 * @author Miłosz Gilga
 */
interface TextKeyExtractor {

	/**
	 * A unique text key identifier associated with a specific message or resource.
	 */
	val textKey: String
}
