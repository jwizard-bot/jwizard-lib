/*
 * Copyright (c) 2025 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.property.extractor

import io.github.cdimascio.dotenv.Dotenv
import pl.jwizard.jwl.util.logger
import java.io.File

/**
 * Extractor responsible for retrieving environment property values from system environment variables and optionally
 * from a `.env` file.
 *
 * @property envFileEnabled Flag indicating whether the `.env` file should be used to load properties.
 * @author Miłosz Gilga
 * @see PropertyValueExtractor
 */
class EnvPropertyValueExtractor(
	private val envFileEnabled: Boolean,
) : PropertyValueExtractor(EnvPropertyValueExtractor::class) {

	companion object {
		private val log = logger<EnvPropertyValueExtractor>()

		/**
		 * The default name of the environment file.
		 */
		private const val ENV_FILE_NAME = ".env"
	}

	/**
	 * Dotenv instance for reading .env file.
	 */
	private val dotEnv = Dotenv
		.configure()
		.ignoreIfMissing()
		.load()

	/**
	 * Sets and retrieves all environment properties. If the `.env` file exists and is enabled, it reads the properties
	 * from that file; otherwise, it retrieves the system environment variables.
	 *
	 * @return A map containing all environment variables, either from the system or from the `.env` file.
	 */
	override fun setProperties(): Map<Any, Any> {
		val file = File(ENV_FILE_NAME)
		if (!file.exists() || !envFileEnabled) {
			if (!envFileEnabled) {
				log.info("Env file disabled. Skipping loading environment variables from {} file.", ENV_FILE_NAME)
			}
			return System.getenv().entries.associate { it.key to it.value }
		}
		val envFileKeys = file.readLines()
			.filter { line -> line.isNotBlank() && !line.startsWith("#") }
			.mapNotNull { line ->
				val key = line.split("=", limit = 2).firstOrNull()?.trim()
				if (key.isNullOrEmpty()) null else key
			}
			.toTypedArray()

		log.info("Extract: {} environment variables from: {} file: {}.", envFileKeys.size, ENV_FILE_NAME, envFileKeys)
		return dotEnv.entries().associate { it.key to it.value }
	}

	override val extractionKey = "env"
}
