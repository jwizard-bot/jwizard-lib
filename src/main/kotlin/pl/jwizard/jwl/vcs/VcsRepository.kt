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
 * @author Miłosz Gilga
 */
enum class VcsRepository(val property: AppBaseProperty) {
	JWIZARD_CORE(AppBaseProperty.VCS_REPOSITORY_JW_CORE),
	JWIZARD_API(AppBaseProperty.VCS_REPOSITORY_JW_API),
	JWIZARD_LIB(AppBaseProperty.VCS_REPOSITORY_JW_LIB),
	JWIZARD_WEB(AppBaseProperty.VCS_REPOSITORY_JW_WEB),
	JWIZARD_TOOLS(AppBaseProperty.VCS_REPOSITORY_JW_TOOLS),
	;
}
