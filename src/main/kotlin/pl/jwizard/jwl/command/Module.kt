/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.command

import pl.jwizard.jwl.DatabaseIdentifier
import pl.jwizard.jwl.i18n.source.I18nModuleSource

/**
 * Enum representing the different modules in the application, each associated with a unique database ID and an
 * internationalization source for localization purposes.
 *
 * @property dbId Unique identifier for the module in the database.
 * @property i18nSource The internationalization source for the module's localization.
 * @author Miłosz Gilga
 */
enum class Module(
	override val dbId: Long,
	val i18nSource: I18nModuleSource,
) : DatabaseIdentifier {

	/**
	 * Music module, responsible for handling music-related commands and functionalities.
	 */
	MUSIC(0, I18nModuleSource.MUSIC),

	/**
	 * DJ module, providing commands related to DJ functionalities and features.
	 */
	DJ(100, I18nModuleSource.DJ),

	/**
	 * Playlist module, managing playlist-related commands and operations.
	 */
	PLAYLIST(200, I18nModuleSource.PLAYLIST),

	/**
	 * Vote module, facilitating voting-related commands and functionalities.
	 */
	VOTE(300, I18nModuleSource.VOTE),

	/**
	 * Other module for handling miscellaneous commands not categorized elsewhere.
	 */
	OTHER(400, I18nModuleSource.OTHER),

	/**
	 * Radio module, managing commands related to radio functionalities and features.
	 */
	RADIO(500, I18nModuleSource.RADIO),
	;
}
