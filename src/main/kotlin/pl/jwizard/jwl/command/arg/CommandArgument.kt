package pl.jwizard.jwl.command.arg

import pl.jwizard.jwl.i18n.I18nLocaleSource

enum class CommandArgument(
	override val placeholder: String,
	vararg val arguments: Argument,
) : I18nLocaleSource {
	TITLE_OR_URL("jw.arg.cmd.titleOrUrl", Argument.TRACK),
	REPEATS_NUMBER("jw.arg.cmd.repeatsNumber", Argument.COUNT),
	VOLUME_UNITS_NUMBER("jw.arg.cmd.volumeUnitsNumber", Argument.VOLUME),
	MEMBER_TAG("jw.arg.cmd.memberTag", Argument.MEMBER),
	POSITION_IN_QUEUE("jw.arg.cmd.positionInQueue", Argument.POS),
	CURRENT_AND_NEW_POS("jw.arg.cmd.currentAndNewPos", Argument.FROM_POS, Argument.TO_POS),
	PLAYLIST_NAME_OR_ID("jw.arg.cmd.playlistNameOrId", Argument.PLAYLIST_NAME_OR_ID),
	PLAYLIST_NAME("jw.arg.cmd.playlistName", Argument.PLAYLIST_NAME),
	RADIO_STATION("jw.arg.cmd.radioStation", Argument.RADIO_STATION),
	PRIVATE("jw.arg.cmd.private", Argument.PRIVATE),
	;
}
