/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.util

/**
 * Singleton class holding constant values and units used for byte formatting.
 *
 * @author Miłosz Gilga
 */
private object Declaration {
	const val ONE_KB = 1024

	/**
	 * Array of units representing byte sizes, ranging from B to EB (Exabytes).
	 */
	val units = arrayOf("B", "KB", "MB", "GB", "TB", "PB", "EB")
}

/**
 * Formats a given number of bytes into a human-readable format.
 *
 * This function takes a number of bytes as input and returns a string representing the size in a more readable format,
 * using appropriate units such as KB, MB, GB, etc. The function handles null input by returning "0 B" and rounds the
 * result to two decimal places.
 *
 * Example usage:
 * ```kotlin
 * formatBytes(null)  // returns "0 B"
 * formatBytes(1500)  // returns "1.46 KB"
 * formatBytes(1048576)  // returns "1.00 MB"
 * ```
 *
 * @param bytes The number of bytes to be formatted. Can be `null`.
 * @return A string representing the byte size in a human-readable format.
 * @author Miłosz Gilga
 */
fun formatBytes(bytes: Long?) = when {
	bytes == null -> "0 B"
	bytes < Declaration.ONE_KB -> "$bytes B"
	else -> {
		var size = bytes.toDouble()
		var unitIndex = 0
		while (size >= Declaration.ONE_KB && unitIndex < Declaration.units.size - 1) {
			size /= Declaration.ONE_KB
			unitIndex++
		}
		"%.2f %s".format(size, Declaration.units[unitIndex])
	}
}
