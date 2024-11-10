/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.command.arg

import pl.jwizard.jwl.TextKeyExtractor
import pl.jwizard.jwl.command.ArgumentOption
import pl.jwizard.jwl.i18n.I18nLocaleSource
import pl.jwizard.jwl.radio.RadioStation

/**
 * Defines various command argument types used in the bot's commands, including metadata for internationalization,
 * argument type, and options.
 *
 * @property placeholder A string key used for localization, which maps to the actual argument description.
 * @property type Specifies the expected type of argument, such as STRING or INTEGER.
 * @property options Additional argument options, implements [ArgumentOption] interface. By default empty list
 * @property required Indicates whether the argument is mandatory for the command.
 * @author Miłosz Gilga
 */
enum class Argument(
	override val placeholder: String,
	val type: ArgumentType,
	val options: List<ArgumentOption> = emptyList(),
	val required: Boolean = true,
) : I18nLocaleSource, TextKeyExtractor {
	TRACK("jw.arg.track", ArgumentType.STRING),
	COUNT("jw.arg.count", ArgumentType.INTEGER),
	VOLUME("jw.arg.volume", ArgumentType.INTEGER),
	MEMBER("jw.arg.member", ArgumentType.MENTIONABLE),
	POS("jw.arg.pos", ArgumentType.INTEGER),
	FROM_POS("jw.arg.fromPos", ArgumentType.INTEGER),
	TO_POS("jw.arg.toPos", ArgumentType.INTEGER),
	PLAYLIST_NAME_OR_ID("jw.arg.playlistNameOrId", ArgumentType.STRING),
	PLAYLIST_NAME("jw.arg.playlistName", ArgumentType.STRING),
	RADIO_STATION("jw.arg.radioStation", ArgumentType.STRING, options = RadioStation.entries),
	PRIVATE("jw.arg.private", ArgumentType.BOOLEAN, required = false),
	;

	override val textKey
		get() = placeholder.substringAfterLast(".")
}
