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

	/**
	 * Argument for a track title or URL.
	 */
	TITLE_OR_URL(I18nCmdArgumentSource.TITLE_OR_URL, Argument.TRACK),

	/**
	 * Argument for the number of repeats.
	 */
	REPEATS_NUMBER(I18nCmdArgumentSource.REPEATS_NUMBER, Argument.COUNT),

	/**
	 * Argument for volume units.
	 */
	VOLUME_UNITS_NUMBER(I18nCmdArgumentSource.VOLUME_UNITS_NUMBER, Argument.VOLUME),

	/**
	 * Argument for tagging a member.
	 */
	MEMBER_TAG(I18nCmdArgumentSource.MEMBER_TAG, Argument.MEMBER),

	/**
	 * Argument for position in the queue.
	 */
	POSITION_IN_QUEUE(I18nCmdArgumentSource.POSITION_IN_QUEUE, Argument.POS),

	/**
	 * Arguments for the current and new position in the queue.
	 */
	CURRENT_AND_NEW_POS(I18nCmdArgumentSource.CURRENT_AND_NEW_POS, arrayOf(Argument.FROM_POS, Argument.TO_POS)),

	/**
	 * Argument for playlist name or ID.
	 */
	PLAYLIST_NAME_OR_ID(I18nCmdArgumentSource.PLAYLIST_NAME_OR_ID, Argument.PLAYLIST_NAME_OR_ID),

	/**
	 * Argument for the playlist name.
	 */
	PLAYLIST_NAME(I18nCmdArgumentSource.PLAYLIST_NAME, Argument.PLAYLIST_NAME),

	/**
	 * Argument for specifying a radio station.
	 */
	RADIO_STATION(I18nCmdArgumentSource.RADIO_STATION, Argument.RADIO_STATION),

	/**
	 * Argument for privacy settings.
	 */
	PRIVATE(I18nCmdArgumentSource.PRIVATE, Argument.PRIVATE);

	/**
	 * Constructor for initializing a CommandArgument with a single argument.
	 */
	constructor(i18nSource: I18nCmdArgumentSource, argument: Argument) : this(i18nSource, arrayOf(argument))
}
