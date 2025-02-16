package pl.jwizard.jwl.vcs

import pl.jwizard.jwl.ioc.stereotype.SingletonComponent
import pl.jwizard.jwl.property.AppBaseProperty
import pl.jwizard.jwl.property.BaseEnvironment

@SingletonComponent
class VcsConfigBean(private val environment: BaseEnvironment) {
	companion object {
		private const val SHORT_SHA_LENGTH = 7
	}

	private val organizationName =
		environment.getProperty<String>(AppBaseProperty.VCS_ORGANIZATION_NAME)

	fun createSnapshotUrl(
		repository: VcsRepository,
		version: String?,
	) = createSnapshotUrl(environment.getProperty<String>(repository.property), version)

	fun createSnapshotUrl(
		repository: String,
		version: String?,
	): Pair<String?, String?> =
		version?.let { createSafeSnapshotUrl(repository, it) } ?: Pair(null, null)

	fun createSafeSnapshotUrl(repository: String, version: String): Pair<String, String> {
		val url = "${createRepositoryUrl(repository)}/tree/${version}"
		return Pair(createShortSha(version), url)
	}

	fun createShortSha(version: String) = version.substring(0, SHORT_SHA_LENGTH)

	fun createRepositoryUrl(name: String) = "https://github.com/${organizationName}/$name"

	fun createRepositoryUrl(
		repository: VcsRepository,
	) = createRepositoryUrl(getRepositoryName(repository))

	fun getRepositoryName(
		repository: VcsRepository,
	) = environment.getProperty<String>(repository.property)
}
