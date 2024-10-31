/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.i18n.source

import pl.jwizard.jwl.i18n.I18nLocaleSource

/**
 * Enum class representing various argument sources for internationalization.
 *
 * @author Miłosz Gilga
 * @see I18nLocaleSource
 */
enum class I18nArgumentSource(override val placeholder: String) : I18nLocaleSource {
	TRACK("jw.arg.track"),
	COUNT("jw.arg.count"),
	VOLUME("jw.arg.volume"),
	MEMBER("jw.arg.member"),
	POS("jw.arg.pos"),
	FROM_POS("jw.arg.fromPos"),
	TO_POS("jw.arg.toPos"),
	PLAYLIST_NAME_OR_ID("jw.arg.playlistNameOrId"),
	PLAYLIST_NAME("jw.arg.playlistName"),
	RADIO_STATION("jw.arg.radioStation"),
	PRIVATE("jw.arg.private"),
	;
}
