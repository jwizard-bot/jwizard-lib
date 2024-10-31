/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.command

import pl.jwizard.jwl.DatabaseIdentifier
import pl.jwizard.jwl.command.arg.CommandArgument
import pl.jwizard.jwl.i18n.source.I18nCommandSource

/**
 * Enum representing various commands in the application, each associated with a unique database ID, module, alias,
 * and optional argument definitions.
 *
 * @property dbId Unique identifier for the command in the database.
 * @property module The module to which the command belongs.
 * @property alias The alias used to invoke the command.
 * @property argumentsDefinition Optional definition of command arguments.
 * @author Miłosz Gilga
 */
enum class Command(
	override val dbId: Long,
	val module: Module,
	val alias: String,
	val argumentsDefinition: CommandArgument? = null,
) : DatabaseIdentifier {

	// music module
	PLAY(1, Module.MUSIC, "pl", CommandArgument.TITLE_OR_URL),
	PAUSE(2, Module.MUSIC, "ps"),
	RESUME(3, Module.MUSIC, "rs"),
	REPEAT(4, Module.MUSIC, "rp", CommandArgument.REPEATS_NUMBER),
	REPEATCLS(5, Module.MUSIC, "rpcl"),
	LOOP(6, Module.MUSIC, "lp"),
	PLAYING(7, Module.MUSIC, "cp"),
	PAUSED(8, Module.MUSIC, "cps"),
	GETVOLUME(9, Module.MUSIC, "gvl"),
	QUEUE(10, Module.MUSIC, "qt"),

	// dj module
	SETVOLUME(101, Module.DJ, "svl", CommandArgument.VOLUME_UNITS_NUMBER),
	VOLUMECLS(102, Module.DJ, "cvl"),
	JOIN(103, Module.DJ, "jchn"),
	TRACKSRM(104, Module.DJ, "rtr", CommandArgument.MEMBER_TAG),
	SHUFFLE(105, Module.DJ, "shq"),
	SKIPTO(106, Module.DJ, "skt", CommandArgument.POSITION_IN_QUEUE),
	SKIP(107, Module.DJ, "sk"),
	CLEAR(108, Module.DJ, "cl"),
	STOP(109, Module.DJ, "st"),
	MOVE(110, Module.DJ, "mv", CommandArgument.CURRENT_AND_NEW_POS),
	INFINITE(111, Module.DJ, "inf"),

	// audio tracks
	ADDTRACKPL(201, Module.PLAYLIST, "apt", CommandArgument.PLAYLIST_NAME_OR_ID),
	ADDQUEUEPL(202, Module.PLAYLIST, "apq", CommandArgument.PLAYLIST_NAME_OR_ID),
	ADDPLAYLIST(203, Module.PLAYLIST, "addpl", CommandArgument.PLAYLIST_NAME),
	PLAYPL(204, Module.PLAYLIST, "pl", CommandArgument.PLAYLIST_NAME_OR_ID),
	SHOWMEMPL(205, Module.PLAYLIST, "smmpl", CommandArgument.MEMBER_TAG),
	SHOWMYPL(206, Module.PLAYLIST, "smpl"),
	SHOWPLTRACKS(207, Module.PLAYLIST, "spltr", CommandArgument.PLAYLIST_NAME_OR_ID),

	// vote
	VSKIP(301, Module.VOTE, "vsk"),
	VSKIPTO(302, Module.VOTE, "vsto", CommandArgument.POSITION_IN_QUEUE),
	VCLEAR(303, Module.VOTE, "vcl"),
	VSTOP(304, Module.VOTE, "vst"),
	VSHUFFLE(305, Module.VOTE, "vshq"),

	// other
	HELP(401, Module.OTHER, "h", CommandArgument.PRIVATE),
	DEBUG(402, Module.OTHER, "db", CommandArgument.PRIVATE),
	SETTINGS(403, Module.OTHER, "stg", CommandArgument.PRIVATE),

	// radio
	PLAYRADIO(501, Module.RADIO, "rd", CommandArgument.RADIO_STATION),
	STOPRADIO(502, Module.RADIO, "rds"),
	RADIOS(503, Module.RADIO, "rda"),
	RADIOINFO(504, Module.RADIO, "ri"),
	;

	/**
	 * The internationalization source associated with the command. Defaults to
	 * [I18nCommandSource.COMMAND_DESCRIPTION_NOT_FOUND] if not found.
	 */
	val i18nSource
		get() = try {
			I18nCommandSource.valueOf(name)
		} catch (_: Exception) {
			I18nCommandSource.COMMAND_DESCRIPTION_NOT_FOUND
		}

	/**
	 * The text ID derived from the internationalization source. If not found, returns the command's name.
	 */
	val textId
		get() = try {
			val i18nSource = I18nCommandSource.valueOf(name)
			i18nSource.placeholder.substringAfterLast(".")
		} catch (_: Exception) {
			name
		}

	/**
	 * The exact arguments required by the command, or an empty array if none are defined.
	 */
	val exactArguments
		get() = argumentsDefinition?.arguments ?: emptyArray()

	/**
	 * Parses the command with the given prefix.
	 *
	 * @param prefix The prefix to prepend to the command's text ID.
	 * @return The complete command string.
	 */
	fun parseWithPrefix(prefix: String) = "${prefix}$textId"
}
