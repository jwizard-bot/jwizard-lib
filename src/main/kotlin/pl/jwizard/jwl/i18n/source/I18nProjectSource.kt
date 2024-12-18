/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.i18n.source

import pl.jwizard.jwl.i18n.I18nLocaleSource

/**
 * Enum representing the sources of different JWizard project components for localization.
 *
 * @property placeholder A string key used for localization, which maps to the actual message.
 * @author Miłosz Gilga
 * @see I18nLocaleSource
 */
enum class I18nProjectSource(override val placeholder: String) : I18nLocaleSource {
	ALL("jw.project.all"),
	CORE("jw.project.core"),
	API("jw.project.api"),
	LIB("jw.project.lib"),
	AUDIO_CLIENT("jw.project.audioClient"),
	WEB_UI("jw.project.webUi"),
	TOOLS("jw.project.tools"),
	;
}
