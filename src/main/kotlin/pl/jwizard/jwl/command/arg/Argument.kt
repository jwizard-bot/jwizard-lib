/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.command.arg

import pl.jwizard.jwl.i18n.source.I18nArgumentSource
import pl.jwizard.jwl.i18n.source.I18nOptionSource

/**
 * Defines various command argument types used in the bot's commands, including metadata for internationalization,
 * argument type, and options.
 *
 * @property i18nSource Provides the i18n source key for localizing argument names.
 * @property type Specifies the expected type of argument, such as STRING or INTEGER.
 * @property required Indicates whether the argument is mandatory for the command.
 * @author Miłosz Gilga
 */
enum class Argument(
	val i18nSource: I18nArgumentSource,
	val type: ArgumentType,
	val required: Boolean = true,
) {
	TRACK(I18nArgumentSource.TRACK, ArgumentType.STRING),
	COUNT(I18nArgumentSource.COUNT, ArgumentType.INTEGER),
	VOLUME(I18nArgumentSource.VOLUME, ArgumentType.INTEGER),
	MEMBER(I18nArgumentSource.MEMBER, ArgumentType.MENTIONABLE),
	POS(I18nArgumentSource.POS, ArgumentType.INTEGER),
	FROM_POS(I18nArgumentSource.FROM_POS, ArgumentType.INTEGER),
	TO_POS(I18nArgumentSource.TO_POS, ArgumentType.INTEGER),
	PLAYLIST_NAME_OR_ID(I18nArgumentSource.PLAYLIST_NAME_OR_ID, ArgumentType.STRING),
	PLAYLIST_NAME(I18nArgumentSource.PLAYLIST_NAME, ArgumentType.STRING),
	RADIO_STATION(I18nArgumentSource.RADIO_STATION, ArgumentType.STRING),
	PRIVATE(I18nArgumentSource.PRIVATE, ArgumentType.BOOLEAN, required = false),
	;

	/**
	 * Extracts a unique key identifier from the `i18nSource` placeholder.
	 */
	val extractedKey
		get() = i18nSource.placeholder.substringAfterLast(".")

	/**
	 * Maps options available for the argument, removing any prefix for simplified access.
	 */
	val options
		get() = I18nOptionSource.entries
			.filter { it.name.startsWith(this.name) }
			.associateBy { it.placeholder.substringAfterLast(".") }
}
