/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.vcs

import pl.jwizard.jwl.property.AppBaseProperty

/**
 * Enum representing various repositories within the JWizard project.
 *
 * Each repository is associated with a specific `AppBaseProperty`, which defines configuration properties
 * (name identifier) for the corresponding repository.
 *
 * @property property The application property associated with this repository.
 * @property standalone Determinate, if repository is standalone (included in contributors) or not.
 * @author Miłosz Gilga
 */
enum class VcsRepository(
	val property: AppBaseProperty,
	val standalone: Boolean,
) {
	JWIZARD_CORE(AppBaseProperty.VCS_REPOSITORY_JW_CORE, true),
	JWIZARD_API(AppBaseProperty.VCS_REPOSITORY_JW_API, true),
	JWIZARD_LIB(AppBaseProperty.VCS_REPOSITORY_JW_LIB, false),
	JWIZARD_WEB(AppBaseProperty.VCS_REPOSITORY_JW_WEB, true),
	JWIZARD_TOOLS(AppBaseProperty.VCS_REPOSITORY_JW_TOOLS, true),
	JWIZARD_AUDIO_CLIENT(AppBaseProperty.VCS_REPOSITORY_JW_AUDIO_CLIENT, true),
	;
}
