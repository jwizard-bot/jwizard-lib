/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.command

import pl.jwizard.jwl.DatabaseIdentifier
import pl.jwizard.jwl.TextKeyExtractor
import pl.jwizard.jwl.command.arg.CommandArgument
import pl.jwizard.jwl.i18n.I18nLocaleSource

/**
 * Enum representing various commands in the application, each associated with a unique database ID, module, alias,
 * and optional argument definitions.
 *
 * @property dbId Unique identifier for the command in the database.
 * @property placeholder A string key used for localization, which maps to the actual command description.
 * @property module The module to which the command belongs.
 * @property alias The alias used to invoke the command.
 * @property argumentsDefinition Optional definition of command arguments.
 * @author Miłosz Gilga
 */
enum class Command(
	override val dbId: Long,
	override val placeholder: String,
	val module: Module,
	val alias: String,
	val argumentsDefinition: CommandArgument? = null,
) : I18nLocaleSource, DatabaseIdentifier, TextKeyExtractor {

	// music module
	PLAY(1, "jw.command.play", Module.MUSIC, "pl", CommandArgument.TITLE_OR_URL),
	PAUSE(2, "jw.command.pause", Module.MUSIC, "ps"),
	RESUME(3, "jw.command.resume", Module.MUSIC, "rs"),
	REPEAT(4, "jw.command.repeat", Module.MUSIC, "rp", CommandArgument.REPEATS_NUMBER),
	REPEATCLS(5, "jw.command.repeatcls", Module.MUSIC, "rpcl"),
	LOOP(6, "jw.command.loop", Module.MUSIC, "lp"),
	PLAYING(7, "jw.command.playing", Module.MUSIC, "cp"),
	PAUSED(8, "jw.command.paused", Module.MUSIC, "cps"),
	GETVOLUME(9, "jw.command.getvolume", Module.MUSIC, "gvl"),
	QUEUE(10, "jw.command.queue", Module.MUSIC, "qt"),

	// dj module
	SETVOLUME(101, "jw.command.setvolume", Module.DJ, "svl", CommandArgument.VOLUME_UNITS_NUMBER),
	VOLUMECLS(102, "jw.command.volumecls", Module.DJ, "cvl"),
	JOIN(103, "jw.command.join", Module.DJ, "jchn"),
	TRACKSRM(104, "jw.command.tracksrm", Module.DJ, "rtr", CommandArgument.MEMBER_TAG),
	SHUFFLE(105, "jw.command.shuffle", Module.DJ, "shq"),
	SKIPTO(106, "jw.command.skipto", Module.DJ, "skt", CommandArgument.POSITION_IN_QUEUE),
	SKIP(107, "jw.command.skip", Module.DJ, "sk"),
	CLEAR(108, "jw.command.clear", Module.DJ, "cl"),
	STOP(109, "jw.command.stop", Module.DJ, "st"),
	MOVE(110, "jw.command.move", Module.DJ, "mv", CommandArgument.CURRENT_AND_NEW_POS),
	INFINITE(111, "jw.command.infinite", Module.DJ, "inf"),

	// audio tracks
	ADDTRACKPL(201, "jw.command.addtrackpl", Module.PLAYLIST, "apt", CommandArgument.PLAYLIST_NAME_OR_ID),
	ADDQUEUEPL(202, "jw.command.addqueuepl", Module.PLAYLIST, "apq", CommandArgument.PLAYLIST_NAME_OR_ID),
	ADDPLAYLIST(203, "jw.command.addplaylist", Module.PLAYLIST, "addpl", CommandArgument.PLAYLIST_NAME),
	PLAYPL(204, "jw.command.playpl", Module.PLAYLIST, "pl", CommandArgument.PLAYLIST_NAME_OR_ID),
	SHOWMEMPL(205, "jw.command.showmempl", Module.PLAYLIST, "smmpl", CommandArgument.MEMBER_TAG),
	SHOWMYPL(206, "jw.command.showmypl", Module.PLAYLIST, "smpl"),
	SHOWPLTRACKS(207, "jw.command.showplsongs", Module.PLAYLIST, "spltr", CommandArgument.PLAYLIST_NAME_OR_ID),

	// vote
	VSKIP(301, "jw.command.vskip", Module.VOTE, "vsk"),
	VSKIPTO(302, "jw.command.vskipto", Module.VOTE, "vsto", CommandArgument.POSITION_IN_QUEUE),
	VCLEAR(303, "jw.command.vclear", Module.VOTE, "vcl"),
	VSTOP(304, "jw.command.vstop", Module.VOTE, "vst"),
	VSHUFFLE(305, "jw.command.vshuffle", Module.VOTE, "vshq"),

	// other
	HELP(401, "jw.command.help", Module.OTHER, "h", CommandArgument.PRIVATE),
	DEBUG(402, "jw.command.debug", Module.OTHER, "db", CommandArgument.PRIVATE),
	SETTINGS(403, "jw.command.settings", Module.OTHER, "stg", CommandArgument.PRIVATE),

	// radio
	PLAYRADIO(501, "jw.command.radio", Module.RADIO, "rd", CommandArgument.RADIO_STATION),
	STOPRADIO(502, "jw.command.radiostop", Module.RADIO, "rds"),
	RADIOS(503, "jw.command.radios", Module.RADIO, "rda"),
	RADIOINFO(504, "jw.command.radioinfo", Module.RADIO, "ri"),
	;

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
	fun parseWithPrefix(prefix: String) = "${prefix}$textKey"

	override val textKey
		get() = placeholder.substringAfterLast(".")
}
