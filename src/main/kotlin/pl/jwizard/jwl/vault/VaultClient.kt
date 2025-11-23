package pl.jwizard.jwl.vault

import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.SimpleClientHttpRequestFactory
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
import org.springframework.vault.VaultException
import org.springframework.vault.authentication.LifecycleAwareSessionManager
import org.springframework.vault.client.RestTemplateBuilder
import org.springframework.vault.client.RestTemplateCustomizer
import org.springframework.vault.client.VaultEndpoint
import org.springframework.vault.core.VaultTemplate
import pl.jwizard.jwl.IrreparableException
import pl.jwizard.jwl.property.AppBaseProperty
import pl.jwizard.jwl.property.BaseEnvironment
import pl.jwizard.jwl.util.logger
import pl.jwizard.jwl.vault.kvgroup.VaultKvGroupProperties
import pl.jwizard.jwl.vault.kvgroup.VaultKvGroupPropertySource
import java.util.*

class VaultClient(private val environment: BaseEnvironment) {
	companion object {
		private val log = logger<VaultClient>()
	}

	private val rawType = environment.getProperty<String>(AppBaseProperty.VAULT_AUTHENTICATION_TYPE)
	private val vaultUrl = environment.getProperty<String>(AppBaseProperty.VAULT_URL)
	private val proxyVerificationHeaderName = environment
		.getProperty<String>(AppBaseProperty.PROXY_VERIFICATION_HEADER_NAME)
	private val proxyVerificationToken = environment
		.getProperty<String>(AppBaseProperty.PROXY_VERIFICATION_TOKEN)

	private var isAuthenticated = false
	private lateinit var vaultTemplate: VaultTemplate
	private lateinit var threadPoolScheduler: ThreadPoolTaskScheduler
	private val authenticationType: VaultAuthenticationType

	init {
		authenticationType = determinateAuthenticationType()
	}

	fun initOnce() {
		try {
			if (isAuthenticated) {
				return // ignore init when vault is already authenticated
			}
			val interceptor = ClientHttpRequestInterceptor { request, body, execution ->
				// add header only, if was provided
				if (proxyVerificationToken.isNotBlank()) {
					request.headers.add(proxyVerificationHeaderName, proxyVerificationToken)
				}
				execution.execute(request, body)
			}
			val restTemplateBuilder = RestTemplateBuilder.builder()
				.endpoint(VaultEndpoint.from(vaultUrl))
				.requestFactory(SimpleClientHttpRequestFactory())
				.customizers(RestTemplateCustomizer {
					it.interceptors.add(interceptor) // add custom proxy interceptor
				})
			val authentication = authenticationType.authenticator
				.authenticate(environment, restTemplateBuilder.build())

			threadPoolScheduler = ThreadPoolTaskScheduler()
			threadPoolScheduler.poolSize = 1
			threadPoolScheduler.threadNamePrefix = "vault-"
			threadPoolScheduler.initialize()

			// automatically refresh and revoke tokens
			val sessionManager = LifecycleAwareSessionManager(
				authentication,
				threadPoolScheduler,
				restTemplateBuilder.build()
			)
			vaultTemplate = VaultTemplate(restTemplateBuilder, sessionManager)
			log.info(
				"Authenticate to vault client: {} with authentication type: {}.",
				vaultUrl,
				authenticationType,
			)
			isAuthenticated = true
		} catch (ex: VaultException) {
			throw IrreparableException(
				this::class,
				"Unable to authenticate Vault. Cause: %s.",
				ex.message,
			)
		}
	}

	// run after grab all properties and save in-memory
	fun revoke() {
		if (authenticationType.authenticator.canRevokeAccess) {
			log.info("Revoked access to vault storage.")
			vaultTemplate.destroy() // run only for dedicated session manager
			threadPoolScheduler.destroy() // MUST destroy scheduler after revoked token
		}
		isAuthenticated = false
	}

	fun readKvPaths(kvPath: String = "", patternFilter: Regex? = null): List<String> {
		val kvBackend = environment.getProperty<String>(AppBaseProperty.VAULT_KV_BACKEND)
		val qualifiedKvPath = if (kvPath != "") {
			"$kvBackend/$kvPath"
		} else {
			kvBackend
		}
		val allKeys = vaultTemplate.list(qualifiedKvPath) ?: emptyList()
		return if (patternFilter != null) {
			allKeys.filter { it.matches(patternFilter) }
		} else {
			allKeys
		}
	}

	fun readKvSecrets(kvStore: String): Properties {
		val kvBackend = environment.getProperty<String>(AppBaseProperty.VAULT_KV_BACKEND)
		val properties = Properties()

		val qualifiedKvStorePath = "$kvBackend/$kvStore"
		val response = vaultTemplate.read(qualifiedKvStorePath)

		if (response != null) {
			response.data?.forEach { properties[it.key] = it.value }
			log.info("Load: {} secrets from: {} KV store.", response.data?.size, qualifiedKvStorePath)
		}
		return properties
	}

	fun <U, T : VaultKvGroupPropertySource> readKvGroupPropertySource(
		kvPath: String = "",
		patternFilter: Regex? = null,
		keyExtractor: (kvGroupName: String) -> U,
		immediatelyRevoke: Boolean = true,
	): Map<U, VaultKvGroupProperties<T>> {
		val kvGroupNames = readKvPaths(kvPath, patternFilter)
		val kvGroupProperties = kvGroupNames.associate {
			keyExtractor(it) to VaultKvGroupProperties<T>(readKvSecrets("$kvPath/$it"))
		}
		if (immediatelyRevoke) {
			revoke()
		}
		return kvGroupProperties
	}

	private fun determinateAuthenticationType() = try {
		VaultAuthenticationType.valueOf(rawType)
	} catch (_: Exception) {
		throw IrreparableException(
			this::class,
			"Followed authentication type: %s is not supported.",
			rawType,
		)
	}
}
