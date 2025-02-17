package pl.jwizard.jwl.property

import kotlin.reflect.KClass

enum class VcsProperty(
	override val key: String,
	override val type: KClass<*> = String::class,
) : AppProperty {
	// all version control system (VCS) repositories
	VCS_ALL("vcs.all"),
	VCS_REPOSITORY_JW_CORE("vcs.repository.jw-core"),
	VCS_REPOSITORY_JW_API("vcs.repository.jw-api"),
	VCS_REPOSITORY_JW_LIB("vcs.repository.jw-lib"),
	VCS_REPOSITORY_JW_WEB("vcs.repository.jw-web"),
	VCS_REPOSITORY_JW_TOOLS("vcs.repository.jw-tools"),
	;
}
