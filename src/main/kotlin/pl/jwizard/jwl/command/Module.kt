/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.command

import pl.jwizard.jwl.DatabaseIdentifier
import pl.jwizard.jwl.i18n.I18nLocaleSource

/**
 * Enum representing the different modules in the application, each associated with a unique database ID and an
 * internationalization source for localization purposes.
 *
 * @property dbId Unique identifier for the module in the database.
 * @property placeholder A string key used for localization, which maps to the actual module name.
 * @author Miłosz Gilga
 */
enum class Module(
	override val dbId: Long,
	override val placeholder: String,
) : DatabaseIdentifier, I18nLocaleSource {
	MUSIC(0, "jw.module.music"),
	DJ(100, "jw.module.dj"),
	PLAYLIST(200, "jw.module.playlist"),
	VOTE(300, "jw.module.vote"),
	OTHER(400, "jw.module.other"),
	RADIO(500, "jw.module.radio"),
	;
}
