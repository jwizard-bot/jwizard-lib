/*
 * Copyright (c) 2025 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.vcs.documented

import pl.jwizard.jwl.vcs.VcsRepository
import pl.jwizard.jwl.vcs.documented.DocumentationType.JAVADOC
import pl.jwizard.jwl.vcs.documented.DocumentationType.KDOC

/**
 * Enum representing repositories that have documentation associated with them.
 *
 * @property repository The version control system (VCS) repository associated with this entry.
 * @property slug A short identifier for the repository, used in URLs or references.
 * @property types The list of documentation types available for this repository.
 * @author Miłosz Gilga
 */
enum class DocumentedRepository(
	val repository: VcsRepository,
	val slug: String,
	vararg val types: DocumentationType,
) {
	JWIZARD_CORE(VcsRepository.JWIZARD_CORE, "jwc", KDOC, JAVADOC),
	JWIZARD_LIB(VcsRepository.JWIZARD_LIB, "jwl", KDOC, JAVADOC),
	JWIZARD_API(VcsRepository.JWIZARD_API, "jwa", KDOC, JAVADOC),
	JWIZARD_AUDIO_CLIENT(VcsRepository.JWIZARD_AUDIO_CLIENT, "jwac", KDOC, JAVADOC),
	;
}
