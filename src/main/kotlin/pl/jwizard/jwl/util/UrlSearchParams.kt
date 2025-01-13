/*
 * Copyright (c) 2025 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.util

import java.net.URLEncoder

/**
 * A utility class for building URLs with query parameters.
 *
 * @property baseUrl The base URL to which query parameters will be appended.
 * @property params A map of query parameter keys and their corresponding values.
 * @author Miłosz Gilga
 */
class UrlSearchParams(
	private val baseUrl: String,
	private val params: Map<String, Any>,
) {

	/**
	 * A builder class for constructing a URL with search parameters. This class provides a fluent interface to set the
	 * base URL and add query parameters.
	 */
	class Builder {

		/**
		 * The base URL for the constructed URL.
		 */
		private var baseUrl: String? = null

		/**
		 * A mutable map storing query parameter keys and their corresponding encoded values.
		 */
		private val params = mutableMapOf<String, Any>()

		/**
		 * Sets the base URL for the URL being constructed.
		 *
		 * @param baseUrl the base URL as a string
		 * @return the current [Builder] instance for method chaining
		 */
		fun setBaseUrl(baseUrl: String) = apply {
			this.baseUrl = baseUrl
		}

		/**
		 * Adds a query parameter to the URL being constructed. The value is automatically encoded using UTF-8.
		 *
		 * @param key the parameter name
		 * @param value the parameter value
		 * @return the current [Builder] instance for method chaining
		 */
		fun addParam(key: String, value: Any) = apply {
			params += key to URLEncoder.encode(value.toString(), Charsets.UTF_8)
		}

		/**
		 * Builds the final URL with the base URL and appended query parameters.
		 *
		 * @return the constructed URL as a string
		 * @throws IllegalArgumentException if the base URL is not set
		 */
		fun build(): String {
			requireNotNull(baseUrl) { "Base URL must not be null." }
			var i = 0
			val builder = StringBuilder(baseUrl)
			for ((key, value) in params) {
				builder.append(if (i++ > 0) "&" else "?")
				builder.append(key)
				builder.append("=")
				builder.append(value)
			}
			return builder.toString()
		}
	}
}
