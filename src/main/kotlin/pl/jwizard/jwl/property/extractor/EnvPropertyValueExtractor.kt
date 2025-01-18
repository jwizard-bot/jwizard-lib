/*
 * Copyright (c) 2025 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.property.extractor

import io.github.cdimascio.dotenv.Dotenv
import pl.jwizard.jwl.util.logger
import java.io.File

/**
 * Extractor for environment property values from system environment variables and an optional .env file.
 *
 * This class is responsible for loading environment variables either from the system environment or an optional `.env`
 * file, depending on the configuration of the [envFileEnabled] flag.
 *
 * @property envFileEnabled Flag indicating whether to load properties from the .env file.
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
	 * Sets the properties for the environment variable extractor.
	 * If the `.env` file is enabled, it loads the variables from that file, otherwise, it skips it.
	 * If the `.env` file is found, it logs the number of variables extracted.
	 *
	 * @return A map of environment variable keys and their corresponding values.
	 */
	override fun setProperties(): Map<Any, Any> {
		val file = File(ENV_FILE_NAME)
		if (!file.exists() || !envFileEnabled) {
			if (!envFileEnabled) {
				log.info("Env file disabled. Skipping loading environment variables from {} file.", ENV_FILE_NAME)
			}
		} else {
			val envFileKeys = file.readLines()
				.filter { line -> line.isNotBlank() && !line.startsWith("#") }
				.mapNotNull { line ->
					val key = line.split("=", limit = 2).firstOrNull()?.trim()
					if (key.isNullOrEmpty()) null else key
				}
				.toTypedArray()

			log.info("Extract: {} environment variables from: {} file: {}.", envFileKeys.size, ENV_FILE_NAME, envFileKeys)
		}
		return getAllEnvVariables()
	}

	/**
	 * Retrieves all environment variables from the Dotenv instance.
	 *
	 * This method returns a map of all the variables loaded from the `.env` file, as well as any system environment
	 * variables that have been read.
	 *
	 * @return A map of environment variable keys and their corresponding values.
	 */
	private fun getAllEnvVariables(): Map<Any, Any> = dotEnv.entries().associate { it.key to it.value }

	override val extractionKey = "env"
}
