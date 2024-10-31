/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.i18n.source

import pl.jwizard.jwl.i18n.source.I18nDynamicMod.ARGS_MOD
import pl.jwizard.jwl.i18n.source.I18nDynamicMod.ARG_OPTION_MOD
import pl.jwizard.jwl.property.Property

/**
 * Enumeration of dynamic property keys for internationalization (i18n) purposes.
 *
 * This enum defines a set of keys used for dynamic property resolution in the i18n system. Each key corresponds
 * to a specific type of dynamic property with a pattern that can be formatted with additional parameters.
 *
 * Defining following properties:
 * - [ARGS_MOD]: The pattern is `jw.arg.%s`, where `%s` is replaced with the argument identifier.
 * - [ARG_OPTION_MOD]: The pattern is `jw.arg.option.%s.%s`, where the first `%s` is replaced with the argument
 *   identifier and the second `%s` is replaced with the option identifier.
 *
 * @property key The property key pattern used for i18n dynamic properties.
 * @author Miłosz Gilga
 */
enum class I18nDynamicMod(override val key: String) : Property {

	/**
	 * The pattern is `jw.arg.%s`, where `%s` is replaced with the argument identifier.
	 */
	ARGS_MOD("jw.arg.%s"),

	/**
	 * The pattern is `jwl.arg.option.%s.%s`, where the first `%s` is replaced with the command identifier and the
	 * second `%s` is replaced with the option identifier.
	 */
	ARG_OPTION_MOD("jw.arg.option.%s.%s"),
}
