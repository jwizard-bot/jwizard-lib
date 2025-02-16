package pl.jwizard.jwl.vcs

import pl.jwizard.jwl.property.AppBaseProperty

enum class VcsRepository(
	val position: Int,
	val property: AppBaseProperty,
) {
	ALL(0, AppBaseProperty.VCS_ALL),
	JWIZARD_CORE(1, AppBaseProperty.VCS_REPOSITORY_JW_CORE),
	JWIZARD_API(2, AppBaseProperty.VCS_REPOSITORY_JW_API),
	JWIZARD_LIB(3, AppBaseProperty.VCS_REPOSITORY_JW_LIB),
	JWIZARD_WEB(5, AppBaseProperty.VCS_REPOSITORY_JW_WEB),
	JWIZARD_TOOLS(6, AppBaseProperty.VCS_REPOSITORY_JW_TOOLS),
	;
}
