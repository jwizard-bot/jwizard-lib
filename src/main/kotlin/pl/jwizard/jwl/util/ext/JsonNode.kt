/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.util.ext

import com.fasterxml.jackson.databind.JsonNode

/**
 * Retrieves the string value associated with the specified key from a [JsonNode].
 *
 * This function returns the value of the specified key as a [String]. If the key does not exist, an empty string is
 * returned by default.
 *
 * @param key The key whose associated value is to be retrieved as text.
 * @return The string value associated with the specified key, or an empty string if the key does not exist.
 * @author Miłosz Gilga
 */
fun JsonNode.getAsText(key: String) = this.get(key).asText()

/**
 * Retrieves the nullable string value associated with the specified key from a [JsonNode].
 *
 * This function returns the value of the specified key as a nullable [String]. If the key does not exist, it returns
 * `null` instead of an empty string.
 *
 * @param key The key whose associated value is to be retrieved as nullable text.
 * @return The nullable string value associated with the specified key, or `null` if the key does not exist.
 * @author Miłosz Gilga
 */
fun JsonNode.getAsNullableText(key: String): String? = this.get(key).asText()

/**
 * Retrieves the long value associated with the specified key from a [JsonNode].
 *
 * This function retrieves the value of the specified key and converts it to a Long. If the key does not exist, it
 * returns 0.
 *
 * @param key The key whose associated value is to be retrieved.
 * @return The long value associated with the specified key, or 0 if the key does not exist.
 * @author Miłosz Gilga
 */
fun JsonNode.getAsLong(key: String) = this.get(key).asLong()

/**
 * Retrieves the integer value associated with the specified key from a [JsonNode].
 *
 * This function retrieves the value of the specified key and converts it to an [Int]. If the key does not exist, it
 * returns 0.
 *
 * @param key The key whose associated value is to be retrieved.
 * @return The integer value associated with the specified key, or 0 if the key does not exist.
 * @author Miłosz Gilga
 */
fun JsonNode.getAsInt(key: String) = this.get(key).asInt()
