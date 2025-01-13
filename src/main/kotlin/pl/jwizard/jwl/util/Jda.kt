/*
 * Copyright (c) 2025 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.util

import java.util.*

/**
 * Extracts the user ID from a token by decoding it. If decoding fails, the function returns null.
 *
 * @param token The encoded token string from which the user ID will be extracted.
 * @return The decoded user ID as a string, or null if decoding fails.
 * @author Miłosz Gilga
 */
fun getUserIdFromToken(token: String): String? = try {
	decodeToken(token)
} catch (e: Exception) {
	null
}

/**
 * Extracts the user ID from a token by decoding it. If decoding fails, the function throws an exception.
 *
 * @param token The encoded token string from which the user ID will be extracted.
 * @return The decoded user ID as a Long.
 * @throws IllegalArgumentException If the token is invalid or decoding fails.
 * @author Miłosz Gilga
 */
fun getUserIdFromTokenWithException(token: String) = try {
	val decodedAsString = decodeToken(token)
		?: throw IllegalArgumentException("Token is not a valid bot token.")
	decodedAsString.toLong()
} catch (e: Exception) {
	throw IllegalArgumentException("Decoding failed: ${e.message}", e)
}

/**
 * Decodes the provided token to extract its first part (user ID). The token must be a valid JWT-like structure
 * containing three parts separated by dots.
 *
 * @param token The encoded token string to be decoded.
 * @return The decoded user ID as a string, or null if the token structure is invalid.
 * @throws IllegalArgumentException If decoding fails or the token is malformed.
 */
private fun decodeToken(token: String): String? {
	val parts = token.split(".")
	if (parts.size != 3) {
		return null
	}
	return String(Base64.getDecoder().decode(parts[0]))
}
