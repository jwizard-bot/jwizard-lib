/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.i18n.source

import pl.jwizard.jwl.i18n.I18nLocaleSource

/**
 * Enum class representing various module sources for internationalization.
 *
 * @author Miłosz Gilga
 * @see I18nLocaleSource
 */
enum class I18nModuleSource(override val placeholder: String) : I18nLocaleSource {
	MUSIC("jw.module.music"),
	DJ("jw.module.dj"),
	PLAYLIST("jw.module.playlist"),
	VOTE("jw.module.vote"),
	OTHER("jw.module.other"),
	RADIO("jw.module.radio"),
	;
}
