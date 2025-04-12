package pl.jwizard.jwl.server.useragent

import pl.jwizard.jwl.http.SecureHttpClientService
import pl.jwizard.jwl.http.UrlSearchParamsBuilder
import pl.jwizard.jwl.property.AppBaseListProperty
import pl.jwizard.jwl.property.AppBaseProperty
import pl.jwizard.jwl.property.BaseEnvironment
import pl.jwizard.jwl.util.logger

class GeolocationProvider(
	private val secureHttpClientService: SecureHttpClientService,
	environment: BaseEnvironment,
) {
	companion object {
		private val log = logger<GeolocationProvider>()
	}

	private val apiUrl = environment.getProperty<String>(AppBaseProperty.GEOLOCATION_API_URL)
	private val apiKey = environment.getProperty<String>(AppBaseProperty.GEOLOCATION_API_KEY)
	private val apiFields = environment
		.getListProperty<String>(AppBaseListProperty.GEOLOCATION_API_FIELDS)

	private val name = environment.getProperty<String>(AppBaseProperty.GEOLOCATION_API_NAME)
	private val website = environment.getProperty<String>(AppBaseProperty.GEOLOCATION_API_WEBSITE)

	// pair: name and website url
	val geolocationApiInfo
		get() = name to website

	fun getGeolocationInfo(ipAddress: String?): String? {
		if (ipAddress == null || apiKey.isBlank()) {
			// geolocation service is disabled
			return null
		}
		val searchUrl = UrlSearchParamsBuilder()
			.setBaseUrl(apiUrl)
			.addParam("apiKey", apiKey)
			.addParam("ip", ipAddress)
			.addParam("fields", apiFields.joinToString())
			.build()

		val node = secureHttpClientService.prepareAndRunSecureHttpRequest(
			url = searchUrl,
			silent = true,
			onErrorCallback = {
				log.error(
					"Unable to fetch geolocation info for IP address: \"{}\". Cause: \"{}\".",
					ipAddress,
					it.message
				)
			}
		) ?: return null
		return apiFields.joinToString { node.get(it).asText() }
	}
}
