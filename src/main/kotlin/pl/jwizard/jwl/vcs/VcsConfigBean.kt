/*
 * Copyright (c) 2025 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.vcs

import pl.jwizard.jwl.ioc.stereotype.SingletonComponent
import pl.jwizard.jwl.property.AppBaseProperty
import pl.jwizard.jwl.property.BaseEnvironment

/**
 * Configuration bean for managing version control system (VCS) settings and generating URLs for specific snapshots in
 * the repository.
 *
 * @property environment An instance of BaseEnvironment, used to retrieve application properties.
 * @author Miłosz Gilga
 */
@SingletonComponent
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
	 * Generates a URL for a specific snapshot in the repository.
	 *
	 * @param repository The repository for which the URL is being generated.
	 * @param version The version or commit hash to generate the snapshot URL for.
	 * @return A pair containing the shortened commit hash and the full snapshot URL.
	 */
	fun createSnapshotUrl(repository: VcsRepository, version: String?) =
		createSnapshotUrl(environment.getProperty<String>(repository.property), version)

	/**
	 * Generates a URL for a specific snapshot in the repository. Returns a pair of `null` values if the version is not
	 * provided.
	 *
	 * @param repository The name of the repository.
	 * @param version The version or commit hash to generate the snapshot URL for.
	 * @return A pair containing the shortened commit hash (if applicable) and the full snapshot URL.
	 */
	fun createSnapshotUrl(repository: String, version: String?): Pair<String?, String?> =
		version?.let { createSafeSnapshotUrl(repository, it) } ?: Pair(null, null)

	/**
	 * Safely generates a URL for a specific snapshot in the repository, ensuring the version is valid.
	 *
	 * @param repository The name of the repository.
	 * @param version The version or commit hash to generate the snapshot URL for.
	 * @return A pair containing the shortened commit hash and the full snapshot URL.
	 */
	fun createSafeSnapshotUrl(repository: String, version: String): Pair<String, String> {
		val url = "${createRepositoryUrl(repository)}/tree/${version}"
		return Pair(createShortSha(version), url)
	}

	/**
	 * Creates a shortened SHA hash for the given version.
	 *
	 * @param version The full SHA hash to be shortened.
	 * @return A shortened SHA hash consisting of the first [SHORT_SHA_LENGTH] characters.
	 */
	fun createShortSha(version: String) = version.substring(0, SHORT_SHA_LENGTH)

	/**
	 * Generates a URL for the repository based on its name.
	 *
	 * @param name The name of the repository.
	 * @return A URL pointing to the repository in the VCS system.
	 */
	fun createRepositoryUrl(name: String) = "https://github.com/${organizationName}/$name"

	/**
	 * Generates a URL for the repository using the [VcsRepository] instance.
	 *
	 * @param repository The [VcsRepository] instance, which contains information about the repository.
	 * @return A URL pointing to the repository in the VCS system.
	 */
	fun createRepositoryUrl(repository: VcsRepository) = createRepositoryUrl(getRepositoryName(repository))

	/**
	 * Retrieves the name of the specified repository from application properties.
	 *
	 * @param repository The [VcsRepository] instance representing the repository whose name is being retrieved.
	 * @return The name of the repository as defined in application properties.
	 */
	fun getRepositoryName(repository: VcsRepository) = environment.getProperty<String>(repository.property)
}
