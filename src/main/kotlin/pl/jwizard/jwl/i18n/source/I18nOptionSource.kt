/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.i18n.source

import pl.jwizard.jwl.i18n.I18nLocaleSource

/**
 * Enum class representing various options in command arguments for internationalization.
 *
 * @author Miłosz Gilga
 * @see I18nLocaleSource
 */
enum class I18nOptionSource(override val placeholder: String) : I18nLocaleSource {
	RADIO_RMF_FM("jw.arg.option.radio.rmfFm"),
	RADIO_RMF_MAXX("jw.arg.option.radio.rmfMaxx"),
	RADIO_ZET("jw.arg.option.radio.zet"),
	RADIO_MELO("jw.arg.option.radio.melo"),
	RADIO_ANTY("jw.arg.option.radio.anty"),
	;
}
