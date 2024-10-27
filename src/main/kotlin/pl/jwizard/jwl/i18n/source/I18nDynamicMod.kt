/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.i18n.source

import pl.jwizard.jwl.i18n.source.I18nDynamicMod.*
import pl.jwizard.jwl.property.Property

/**
 * Enumeration of dynamic property keys for internationalization (i18n) purposes.
 *
 * This enum defines a set of keys used for dynamic property resolution in the i18n system. Each key corresponds
 * to a specific type of dynamic property with a pattern that can be formatted with additional parameters.
 *
 * Defining following properties:
 *
 * - [MODULES_MOD]: The pattern is `jw.module.%s`, where `%s` is replaced with the module identifier.
 * - [ARGS_MOD]: The pattern is `jw.arg.%s`, where `%s` is replaced with the argument identifier.
 * - [ARG_PER_COMMAND_MOD]: The pattern is `jw.arg.combined.%s`, where `%s` is replaced with the combined argument
 *   identifier.
 * - [ARG_OPTION_MOD]: The pattern is `jw.arg.option.%s.%s`, where the first `%s` is replaced with the argument
 *   identifier and the second `%s` is replaced with the option identifier.
 * - [COMMANDS_MOD]: The pattern is `jw.command.%s`, where `%s` is replaced with the command identifier.
 *
 * @property key The property key pattern used for i18n dynamic properties.
 * @author Miłosz Gilga
 */
enum class I18nDynamicMod(override val key: String) : Property {

	/**
	 * The pattern is `jwl.module.%s`, where `%s` is replaced with the module identifier.
	 */
	MODULES_MOD("jwl.module.%s"),

	/**
	 * The pattern is `jwl.arg.%s`, where `%s` is replaced with the argument identifier.
	 */
	ARGS_MOD("jwl.arg.%s"),

	/**
	 * The pattern is `jwl.arg.combined.%s`, where `%s` is replaced with the combined argument identifier
	 * (multiple arguments definition per command).
	 */
	ARG_PER_COMMAND_MOD("jwl.arg.combined.%s"),

	/**
	 * The pattern is `jwl.arg.option.%s.%s`, where the first `%s` is replaced with the command identifier and the
	 * second `%s` is replaced with the option identifier.
	 */
	ARG_OPTION_MOD("jwl.arg.option.%s.%s"),

	/**
	 * The pattern is `jwl.command.%s`, where `%s` is replaced with the command identifier.
	 */
	COMMANDS_MOD("jwl.command.%s"),
	;
}
