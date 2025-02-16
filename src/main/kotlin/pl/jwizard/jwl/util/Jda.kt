package pl.jwizard.jwl.util

import java.util.*

fun getUserIdFromToken(token: String): String? = try {
	decodeToken(token)
} catch (e: Exception) {
	null
}

fun getUserIdFromTokenWithException(token: String) = try {
	val decodedAsString = decodeToken(token)
		?: throw IllegalArgumentException("Token is not a valid bot token.")
	decodedAsString.toLong()
} catch (e: Exception) {
	throw IllegalArgumentException("Decoding failed: ${e.message}", e)
}

private fun decodeToken(token: String): String? {
	val parts = token.split(".")
	if (parts.size != 3) {
		return null
	}
	return String(Base64.getDecoder().decode(parts[0]))
}
