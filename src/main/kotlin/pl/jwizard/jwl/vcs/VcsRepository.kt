/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.vcs

import pl.jwizard.jwl.i18n.source.I18nProjectSource
import pl.jwizard.jwl.property.AppBaseProperty

/**
 * Enum representing various repositories within the JWizard project.
 *
 * Each repository is associated with a specific `AppBaseProperty`, which defines configuration properties
 * (name identifier) for the corresponding repository.
 *
 * @property position Position number in UI (for sorted unordered collections, as [Map]).
 * @property property The application property associated with this repository.
 * @property i18nSource The source for the translated repository name.
 * @author Miłosz Gilga
 */
enum class VcsRepository(
	val position: Int,
	val property: AppBaseProperty,
	val i18nSource: I18nProjectSource,
) {
	JWIZARD_CORE(1, AppBaseProperty.VCS_REPOSITORY_JW_CORE, I18nProjectSource.CORE),
	JWIZARD_API(2, AppBaseProperty.VCS_REPOSITORY_JW_API, I18nProjectSource.API),
	JWIZARD_LIB(3, AppBaseProperty.VCS_REPOSITORY_JW_LIB, I18nProjectSource.LIB),
	JWIZARD_AUDIO_CLIENT(4, AppBaseProperty.VCS_REPOSITORY_JW_AUDIO_CLIENT, I18nProjectSource.AUDIO_CLIENT),
	JWIZARD_WEB(5, AppBaseProperty.VCS_REPOSITORY_JW_WEB, I18nProjectSource.WEB_UI),
	JWIZARD_TOOLS(6, AppBaseProperty.VCS_REPOSITORY_JW_TOOLS, I18nProjectSource.TOOLS),
	;
}
