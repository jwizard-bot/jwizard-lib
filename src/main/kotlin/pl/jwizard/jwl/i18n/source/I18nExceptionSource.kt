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
	UNEXPECTED_EXCEPTION(0, "jw.exception.unexpectedException"),
	MODULE_IS_TURNED_OFF(1, "jw.exception.moduleIsTurnedOffException"),
	EPHEMERAL_UNEXPECTED_EXCEPTION(2, "jw.exception.ephemeralUnexpectedException"),
	INSUFFICIENT_PERMISSIONS_EXCEPTION(3, "jw.exception.insufficientPermissionsException"),

	TRACK_OFFSET_OUT_OF_BOUNDS(100, "jw.exception.trackOffsetOutOfBoundsException"),
	TRACK_THE_SAME_POSITION(101, "jw.exception.trackTheSamePositionException"),
	TRACK_QUEUE_IS_EMPTY(102, "jw.exception.trackQueueIsEmptyException"),
	INVOKER_IS_NOT_SENDER_OR_SUPERUSER(103, "jw.exception.invokerIsNotSenderOrSuperuserException"),
	USER_NOT_ADDED_TRACKS_TO_QUEUE(104, "jw.exception.userNotAddedTracksToQueueException"),
	PLAYER_NOT_PAUSED(105, "jw.exception.playerNotPausedException"),
	ACTIVE_AUDIO_PLAYING_NOT_FOUND(106, "jw.exception.activeAudioPlayingNotFoundException"),
	TEMPORARY_HALTED_BOT(108, "jw.exception.temporaryHaltedBotException"),
	TRACK_REPEATS_OUT_OF_BOUNDS(109, "jw.exception.trackRepeatsOutOfBoundsException"),
	ISSUE_WHILE_PLAYING_TRACK(110, "jw.exception.unexpectedErrorOnPlayTrack"),
	ISSUE_WHILE_LOADING_TRACK(111, "jw.exception.unexpectedErrorOnLoadTrack"),
	NOT_FOUND_TRACK(112, "jw.exception.notFoundAudioTrack"),

	USER_ID_ALREADY_WITH_BOT(200, "jw.exception.userIsAlreadyWithBotException"),
	USER_NOT_FOUND_IN_GUILD(201, "jw.exception.userNotFoundInGuildException"),
	UNAUTHORIZED_DJ(202, "jw.exception.unauthorizedDjException"),
	UNAUTHORIZED_DJ_OR_SENDER(203, "jw.exception.unauthorizedDjOrSenderException"),
	UNAUTHORIZED_MANAGER(204, "jw.exception.unauthorizedManagerException"),
	USER_ON_VOICE_CHANNEL_NOT_FOUND(205, "jw.exception.userOnVoiceChannelNotFoundException"),
	USER_ON_VOICE_CHANNEL_WITH_BOT_NOT_FOUND(206, "jw.exception.userOnVoiceChannelWithBotNotFoundException"),

	FORBIDDEN_CHANNEL(300, "jw.exception.forbiddenChannelException"),
	VOLUME_UNITS_OUT_OF_BOUNDS(301, "jw.exception.volumeUnitsOutOfBoundsException"),
	COMMAND_IS_TURNED_OFF(302, "jw.exception.commandIsTurnedOffException"),
	MISMATCH_COMMAND_ARGS(303, "jw.exception.mismatchCommandArgumentsException"),
	VIOLATED_COMMAND_ARG_OPTIONS(304, "jw.exception.violatedCommandArgumentOptionsException"),
	COMMAND_AVAILABLE_ONLY_FOR_DISCRETE_TRACK(305, "jw.exception.commandAvailableOnlyForDiscreteTrackException"),

	RADIO_STATION_NOT_EXISTS_IS_TURNED_OFF(400, "jw.exception.radioStationNotExistsOrTurnedOffException"),
	RADIO_STATION_IS_NOT_PLAYING(401, "jw.exception.radioStationIsNotPlayingException"),
	RADIO_STATION_IS_PLAYING(402, "jw.exception.radioStationIsPlayingException"),
	DISCRETE_AUDIO_STREAM_IS_PLAYING(403, "jw.exception.discreteAudioStreamIsPlayingException"),
	UNEXPECTED_ERROR_ON_LOAD_RADIO(404, "jw.exception.unexpectedErrorOnLoadRadioException"),
	UNEXPECTED_ERROR_WHILE_STREAMING_RADIO(405, "jw.exception.unexpectedErrorWhileStreamingRadioException"),
	RADIO_STATION_NOT_PROVIDING_PLAYBACK_DATA(406, "jw.exception.radioStationNotProvidingPlaybackDataException"),
	;
}
