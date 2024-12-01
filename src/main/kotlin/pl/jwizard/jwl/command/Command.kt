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
 * @property argumentsDefinition Optional definition of command arguments.
 * @property global Determines if command also can be executed in global environment (without guild reference).
 * @author Miłosz Gilga
 */
enum class Command(
	override val dbId: Long,
	override val placeholder: String,
	val module: Module,
	val argumentsDefinition: CommandArgument? = null,
	val global: Boolean = false,
) : I18nLocaleSource, DatabaseIdentifier, TextKeyExtractor {

	// music module
	PLAY(1, "jw.command.play", Module.MUSIC, CommandArgument.TITLE_OR_URL),
	PAUSE(2, "jw.command.pause", Module.MUSIC),
	RESUME(3, "jw.command.resume", Module.MUSIC),
	REPEAT_SET(4, "jw.command.repeat.set", Module.MUSIC, CommandArgument.REPEATS_NUMBER),
	REPEAT_CLEAR(5, "jw.command.repeat.clear", Module.MUSIC),
	INFINITE(6, "jw.command.infinite", Module.MUSIC),
	PLAYING(7, "jw.command.playing", Module.MUSIC),
	PAUSED(8, "jw.command.paused", Module.MUSIC),
	VOLUME(9, "jw.command.volume", Module.MUSIC),
	QUEUE_SHOW(10, "jw.command.queue.show", Module.MUSIC),

	// dj module
	VOLUME_SET(101, "jw.command.volume.set", Module.DJ, CommandArgument.VOLUME_UNITS_NUMBER),
	VOLUME_CLEAR(102, "jw.command.volume.clear", Module.DJ),
	JOIN(103, "jw.command.join", Module.DJ),
	QUEUE_REMOVE(104, "jw.command.queue.remove", Module.DJ, CommandArgument.MEMBER_TAG),
	QUEUE_SHUFFLE(105, "jw.command.queue.shuffle", Module.DJ),
	SKIPTO(106, "jw.command.skipto", Module.DJ, CommandArgument.POSITION_IN_QUEUE),
	SKIP(107, "jw.command.skip", Module.DJ),
	QUEUE_CLEAR(108, "jw.command.queue.clear", Module.DJ),
	STOP(109, "jw.command.stop", Module.DJ),
	QUEUE_MOVE(110, "jw.command.queue.move", Module.DJ, CommandArgument.CURRENT_AND_NEW_POS),
	QUEUE_INFINITE(111, "jw.command.queue.infinite", Module.DJ),

	// playlists
	PLAYLIST_ADDTRACK(201, "jw.command.playlist.add.track", Module.PLAYLIST, CommandArgument.PLAYLIST_NAME_OR_ID),
	PLAYLIST_ADDQUEUE(202, "jw.command.playlist.add.queue", Module.PLAYLIST, CommandArgument.PLAYLIST_NAME_OR_ID),
	PLAYLIST_CREATE(203, "jw.command.playlist.create", Module.PLAYLIST, CommandArgument.PLAYLIST_NAME),
	PLAYLIST_PLAY(204, "jw.command.playlist.play", Module.PLAYLIST, CommandArgument.PLAYLIST_NAME_OR_ID),
	PLAYLIST_MEMBER(205, "jw.command.playlist.member", Module.PLAYLIST, CommandArgument.MEMBER_TAG),
	PLAYLIST_ME(206, "jw.command.playlist.me", Module.PLAYLIST),
	PLAYLIST_TRACKS(207, "jw.command.playlist.tracks", Module.PLAYLIST, CommandArgument.PLAYLIST_NAME_OR_ID),

	// vote
	VOTE_SKIP(301, "jw.command.vote.skip", Module.VOTE),
	VOTE_SKIPTO(302, "jw.command.vote.skipto", Module.VOTE, CommandArgument.POSITION_IN_QUEUE),
	VOTE_QUEUE_CLEAR(303, "jw.command.vote.queue.clear", Module.VOTE),
	VOTE_STOP(304, "jw.command.vote.stop", Module.VOTE),
	VOTE_QUEUE_SHUFFLE(305, "jw.command.vote.queue.shuffle", Module.VOTE),

	// other
	HELP(401, "jw.command.help", Module.OTHER, CommandArgument.PRIVATE, true),
	DEBUG(402, "jw.command.debug", Module.OTHER, CommandArgument.PRIVATE),
	SETTINGS(403, "jw.command.settings", Module.OTHER, CommandArgument.PRIVATE),

	// radio
	RADIO_PLAY(501, "jw.command.radio.play", Module.RADIO, CommandArgument.RADIO_STATION),
	RADIO_STOP(502, "jw.command.radio.stop", Module.RADIO),
	RADIO_ALL(503, "jw.command.radio.all", Module.RADIO),
	RADIO_INFO(504, "jw.command.radio.info", Module.RADIO),
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
		get() = placeholder.replace("jw.command.", "")
}
