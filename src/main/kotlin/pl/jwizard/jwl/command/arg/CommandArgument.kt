/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.command.arg

import pl.jwizard.jwl.i18n.source.I18nCmdArgumentSource

/**
 * Enum representing various command arguments, each associated with its internationalization source
 * and the corresponding argument values.
 *
 * @property i18nSource The internationalization source for the argument.
 * @property arguments The array of arguments associated with the command.
 * @author Miłosz Gilga
 */
enum class CommandArgument(
	val i18nSource: I18nCmdArgumentSource,
	val arguments: Array<Argument>,
) {
	TITLE_OR_URL(I18nCmdArgumentSource.TITLE_OR_URL, Argument.TRACK),
	REPEATS_NUMBER(I18nCmdArgumentSource.REPEATS_NUMBER, Argument.COUNT),
	VOLUME_UNITS_NUMBER(I18nCmdArgumentSource.VOLUME_UNITS_NUMBER, Argument.VOLUME),
	MEMBER_TAG(I18nCmdArgumentSource.MEMBER_TAG, Argument.MEMBER),
	POSITION_IN_QUEUE(I18nCmdArgumentSource.POSITION_IN_QUEUE, Argument.POS),
	CURRENT_AND_NEW_POS(I18nCmdArgumentSource.CURRENT_AND_NEW_POS, arrayOf(Argument.FROM_POS, Argument.TO_POS)),
	PLAYLIST_NAME_OR_ID(I18nCmdArgumentSource.PLAYLIST_NAME_OR_ID, Argument.PLAYLIST_NAME_OR_ID),
	PLAYLIST_NAME(I18nCmdArgumentSource.PLAYLIST_NAME, Argument.PLAYLIST_NAME),
	RADIO_STATION(I18nCmdArgumentSource.RADIO_STATION, Argument.RADIO_STATION),
	PRIVATE(I18nCmdArgumentSource.PRIVATE, Argument.PRIVATE);

	/**
	 * Constructor for initializing a CommandArgument with a single argument.
	 */
	constructor(i18nSource: I18nCmdArgumentSource, argument: Argument) : this(i18nSource, arrayOf(argument))
}
