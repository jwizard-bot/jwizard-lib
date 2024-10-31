/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.i18n.source

import pl.jwizard.jwl.i18n.I18nLocaleSource

/**
 * Enum class representing various command argument sources for internationalization.
 *
 * @author Miłosz Gilga
 * @see I18nLocaleSource
 */
enum class I18nCmdArgumentSource(override val placeholder: String) : I18nLocaleSource {
	TITLE_OR_URL("jw.arg.cmd.titleOrUrl"),
	REPEATS_NUMBER("jw.arg.cmd.repeatsNumber"),
	VOLUME_UNITS_NUMBER("jw.arg.cmd.volumeUnitsNumber"),
	MEMBER_TAG("jw.arg.cmd.memberTag"),
	POSITION_IN_QUEUE("jw.arg.cmd.positionInQueue"),
	CURRENT_AND_NEW_POS("jw.arg.cmd.currentAndNewPos"),
	PLAYLIST_NAME_OR_ID("jw.arg.cmd.playlistNameOrId"),
	PLAYLIST_NAME("jw.arg.cmd.playlistName"),
	RADIO_STATION("jw.arg.cmd.radioStation"),
	PRIVATE("jw.arg.cmd.private"),
	;
}
