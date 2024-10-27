/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.i18n.source

import pl.jwizard.jwl.i18n.I18nLocaleSource

/**
 * Enum representing various internationalization exception sources.
 *
 * This enum defines a set of exceptions that can be thrown in the application, each associated with a specific error
 * tracker ID and a placeholder for localization. It implements the [I18nLocaleSource] interface, which provides a
 * structure for internationalization of error messages.
 *
 * @property tracker An integer representing the unique identifier for the exception.
 * @property placeholder A string key used for localization, which maps to the actual error message.
 * @author Miłosz Gilga
 * @see I18nLocaleSource
 */
enum class I18nExceptionSource(
	val tracker: Int,
	override val placeholder: String,
) : I18nLocaleSource {
	UNEXPECTED_EXCEPTION(0, "jwl.exception.unexpectedException"),
	MODULE_IS_TURNED_OFF(1, "jwl.exception.moduleIsTurnedOffException"),
	EPHEMERAL_UNEXPECTED_EXCEPTION(2, "jwl.exception.ephemeralUnexpectedException"),

	TRACK_OFFSET_OUT_OF_BOUNDS(100, "jwl.exception.trackOffsetOutOfBoundsException"),
	TRACK_THE_SAME_POSITION(101, "jwl.exception.trackTheSamePositionException"),
	TRACK_QUEUE_IS_EMPTY(102, "jwl.exception.trackQueueIsEmptyException"),
	INVOKER_IS_NOT_SENDER_OR_SUPERUSER(103, "jwl.exception.invokerIsNotSenderOrSuperuserException"),
	USER_NOT_ADDED_TRACKS_TO_QUEUE(104, "jwl.exception.userNotAddedTracksToQueueException"),
	PLAYER_NOT_PAUSED(105, "jwl.exception.playerNotPausedException"),
	ACTIVE_AUDIO_PLAYING_NOT_FOUND(106, "jwl.exception.activeAudioPlayingNotFoundException"),
	TEMPORARY_HALTED_BOT(108, "jwl.exception.temporaryHaltedBotException"),
	TRACK_REPEATS_OUT_OF_BOUNDS(109, "jwl.exception.trackRepeatsOutOfBoundsException"),
	ISSUE_WHILE_PLAYING_TRACK(110, "jwl.exception.unexpectedErrorOnPlayTrack"),
	ISSUE_WHILE_LOADING_TRACK(111, "jwl.exception.unexpectedErrorOnLoadTrack"),
	NOT_FOUND_TRACK(112, "jwl.exception.notFoundAudioTrack"),

	USER_ID_ALREADY_WITH_BOT(200, "jwl.exception.userIsAlreadyWithBotException"),
	USER_NOT_FOUND_IN_GUILD(201, "jwl.exception.userNotFoundInGuildException"),
	UNAUTHORIZED_DJ(202, "jwl.exception.unauthorizedDjException"),
	UNAUTHORIZED_DJ_OR_SENDER(203, "jwl.exception.unauthorizedDjOrSenderException"),
	UNAUTHORIZED_MANAGER(204, "jwl.exception.unauthorizedManagerException"),
	USER_ON_VOICE_CHANNEL_NOT_FOUND(205, "jwl.exception.userOnVoiceChannelNotFoundException"),
	USER_ON_VOICE_CHANNEL_WITH_BOT_NOT_FOUND(206, "jwl.exception.userOnVoiceChannelWithBotNotFoundException"),

	FORBIDDEN_CHANNEL(300, "jwl.exception.forbiddenChannelException"),
	VOLUME_UNITS_OUT_OF_BOUNDS(301, "jwl.exception.volumeUnitsOutOfBoundsException"),
	COMMAND_IS_TURNED_OFF(302, "jwl.exception.commandIsTurnedOffException"),
	MISMATCH_COMMAND_ARGS(303, "jwl.exception.mismatchCommandArgumentsException"),
	VIOLATED_COMMAND_ARG_OPTIONS(304, "jwl.exception.violatedCommandArgumentOptionsException"),
	COMMAND_AVAILABLE_ONLY_FOR_DISCRETE_TRACK(305, "jwl.exception.commandAvailableOnlyForDiscreteTrackException"),

	RADIO_STATION_NOT_EXISTS_IS_TURNED_OFF(400, "jwl.exception.radioStationNotExistsOrTurnedOffException"),
	RADIO_STATION_IS_NOT_PLAYING(401, "jwl.exception.radioStationIsNotPlayingException"),
	RADIO_STATION_IS_PLAYING(402, "jwl.exception.radioStationIsPlayingException"),
	DISCRETE_AUDIO_STREAM_IS_PLAYING(403, "jwl.exception.discreteAudioStreamIsPlayingException"),
	UNEXPECTED_ERROR_ON_LOAD_RADIO(404, "jwl.exception.unexpectedErrorOnLoadRadioException"),
	UNEXPECTED_ERROR_WHILE_STREAMING_RADIO(405, "jwl.exception.unexpectedErrorWhileStreamingRadioException"),
	RADIO_STATION_NOT_PROVIDING_PLAYBACK_DATA(406, "jwl.exception.radioStationNotProvidingPlaybackDataException"),
	;
}
