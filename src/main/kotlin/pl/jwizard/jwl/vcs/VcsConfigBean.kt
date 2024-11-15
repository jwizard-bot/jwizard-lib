/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.vcs

import org.springframework.stereotype.Component
import pl.jwizard.jwl.property.AppBaseProperty
import pl.jwizard.jwl.property.BaseEnvironment

/**
 * Configuration bean for managing version control system (VCS) settings and generating URLs for specific snapshots in
 * the repository.
 *
 * @property environment An instance of BaseEnvironment, used to retrieve application properties.
 * @author Miłosz Gilga
 */
@Component
class VcsConfigBean(private val environment: BaseEnvironment) {

	companion object {
		/**
		 * Length of the shortened SHA hash used for identifying a specific commit version.
		 */
		private const val SHORT_SHA_LENGTH = 7
	}

	/**
	 * The name of the organization owning the repository, as retrieved from application properties.
	 */
	private val organizationName = environment.getProperty<String>(AppBaseProperty.VCS_ORGANIZATION_NAME)

	/**
	 * Generates a URL for a specific snapshot in the repository based on the repository identifier and commit version.
	 *
	 * @param repository The [VcsRepository] instance, which contains information about the repository.
	 * @param version The commit SHA or version tag for which the snapshot URL is generated.
	 *
	 * @return A pair containing a shortened version of the commit SHA (or `null` if not available) and the snapshot URL
	 *         in the repository, or null if version information is unavailable.
	 */
	fun createSnapshotUrl(repository: VcsRepository, version: String?): Pair<String?, String?> {
		if (version != null) {
			val repositoryName = environment.getProperty<String>(repository.property)
			val url = "https://github.com/${organizationName}/${repositoryName}/tree/${version}"
			return Pair(version.substring(0, SHORT_SHA_LENGTH), url)
		}
		return Pair(null, null)
	}

	/**
	 * Retrieves the name of the specified repository from application properties.
	 *
	 * @param repository The [VcsRepository] instance representing the repository whose name is being retrieved.
	 * @return The name of the repository as defined in application properties.
	 */
	fun getRepositoryName(repository: VcsRepository) = environment.getProperty<String>(repository.property)
}
