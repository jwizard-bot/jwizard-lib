package pl.jwizard.jwl.server.useragent

import com.fasterxml.jackson.databind.ObjectMapper
import pl.jwizard.jwl.property.AppBaseListProperty
import pl.jwizard.jwl.property.AppBaseProperty
import pl.jwizard.jwl.property.BaseEnvironment
import pl.jwizard.jwl.server.UrlSearchParamsBuilder
import pl.jwizard.jwl.util.logger
import java.io.IOException
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class GeolocationProvider(
	private val httpClient: HttpClient,
	private val objectMapper: ObjectMapper,
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

		val req = HttpRequest.newBuilder()
			.uri(URI.create(searchUrl))
			.build()

		return try {
			val response = httpClient.send(req, HttpResponse.BodyHandlers.ofString())
			if (response.statusCode() == 200) {
				val parsed = objectMapper.readTree(response.body())
				return apiFields.joinToString { parsed.get(it).asText() }
			} else {
				null
			}
		} catch (ex: IOException) {
			log.error(
				"Unable to fetch geolocation info for IP address: \"{}\". Cause: \"{}\".",
				ipAddress,
				ex.message,
			)
			null
		}
	}
}
