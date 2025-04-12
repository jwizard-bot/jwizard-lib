package pl.jwizard.jwl.http

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.eclipse.jetty.http.HttpHeader
import org.eclipse.jetty.http.HttpMethod
import pl.jwizard.jwl.property.AppBaseProperty
import pl.jwizard.jwl.property.BaseEnvironment
import java.io.IOException
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpRequest.BodyPublishers
import java.net.http.HttpResponse

class SecureHttpClientService(
	private val httpClient: HttpClient,
	private val objectMapper: ObjectMapper,
	environment: BaseEnvironment,
) {
	private val proxyVerificationHeaderName = environment
		.getProperty<String>(AppBaseProperty.PROXY_VERIFICATION_HEADER_NAME)
	private val proxyVerificationToken = environment
		.getProperty<String>(AppBaseProperty.PROXY_VERIFICATION_TOKEN)

	fun prepareAndRunSecureHttpRequest(
		url: String,
		authToken: String? = null,
		authTokenType: AuthTokenType = AuthTokenType.PLAIN,
		httpMethod: HttpMethod = HttpMethod.GET,
		contentType: ApiContentType = ApiContentType.APPLICATION_JSON,
		body: String? = null,
		headers: Map<AppHttpHeader, String> = emptyMap(),
		silent: Boolean = false,
		withProxyVerification: Boolean = false,
		onErrorCallback: (ex: Throwable) -> Unit = {},
	): JsonNode? {
		val httpRequest = HttpRequest.newBuilder()
			.uri(URI.create(url))
			.method(
				httpMethod.asString(),
				body?.let { BodyPublishers.ofString(it) } ?: BodyPublishers.noBody(),
			)
			.header(HttpHeader.CONTENT_TYPE.asString(), contentType.mime)
			.apply {
				if (proxyVerificationToken.isNotBlank() && withProxyVerification) {
					header(proxyVerificationHeaderName, proxyVerificationToken)
				}
				authToken?.let { header(HttpHeader.AUTHORIZATION.asString(), authTokenType.type + it) }
				headers.forEach { header(it.key.headerName, it.value) }
			}
			.build()
		try {
			val response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString())
			return if (response.statusCode() == 200) {
				objectMapper.readTree(response.body())
			} else {
				null
			}
		} catch (ex: IOException) {
			onErrorCallback(ex)
			if (silent) {
				return null
			}
			throw ex
		}
	}
}
