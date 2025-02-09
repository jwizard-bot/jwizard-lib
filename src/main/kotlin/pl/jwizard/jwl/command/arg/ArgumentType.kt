/*
 * Copyright (c) 2025 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.command.arg

import pl.jwizard.jwl.i18n.I18nLocaleSource

/**
 * Represents the various types of command arguments and their conversion logic. Each type defines how a string value
 * should be cast to its appropriate data type.
 *
 * @property placeholder A string key used for localization, which maps to the actual argument type description.
 * @property castTo A function that defines how to convert a string value to the corresponding type.
 * @author Miłosz Gilga
 */
enum class ArgumentType(
	override val placeholder: String,
	val castTo: (value: String) -> Any,
) : I18nLocaleSource {

	/**
	 * Represents a string type.
	 * No conversion is needed; the value is returned as is.
	 */
	STRING("jw.type.string", { it }),

	/**
	 * Represents an integer type.
	 * The string value is parsed as an integer using [String.toInt].
	 */
	INTEGER("jw.type.int", { it.toInt() }),

	/**
	 * Represents a mentionable type (ex. users).
	 * If the value contains a mention format, it strips the mention tags.
	 */
	MENTIONABLE("jw.type.mentionable", { (if (it.contains("@")) it.replace(Regex("<@|>"), "") else it).toLong() }),

	/**
	 * Represents a channel type.
	 * If the value contains a channel mention format, it strips the channel tags.
	 */
	CHANNEL("jw.type.channel", { (if (it.contains("#")) it.replace(Regex("<#|>"), "") else it).toLong() }),

	/**
	 * Represents a boolean type.
	 * The string value is converted to a boolean using [String.toBoolean].
	 */
	BOOLEAN("jw.type.boolean", { it.toBoolean() }),
	;
}
