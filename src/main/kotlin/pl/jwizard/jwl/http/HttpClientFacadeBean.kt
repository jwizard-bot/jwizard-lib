package pl.jwizard.jwl.http

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import pl.jwizard.jwl.ioc.stereotype.SingletonComponent
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

@SingletonComponent
class HttpClientFacadeBean(private val objectMapper: ObjectMapper) {
	private val httpClient = HttpClient.newHttpClient()

	// return elements as json nodes list
	fun getJsonListCall(url: String, headers: Map<String, String> = emptyMap()): JsonNode {
		val httpRequest = HttpRequest.newBuilder()
			.uri(URI.create(url))
			.apply { headers.forEach { header(it.key, it.value) } }
			.build()
		val response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString())
		if (response.statusCode() != 200) {
			throw RuntimeException(
				"Could not perform call: ${response.uri()}. Ended code: ${response.statusCode()}.",
			)
		}
		return objectMapper.readTree(response.body())
	}
}
