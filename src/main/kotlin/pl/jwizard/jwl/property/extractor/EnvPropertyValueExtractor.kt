package pl.jwizard.jwl.property.extractor

import io.github.cdimascio.dotenv.Dotenv
import pl.jwizard.jwl.util.logger
import java.io.File

internal class EnvPropertyValueExtractor(
	private val envFileEnabled: Boolean,
) : PropertyValueExtractor(EnvPropertyValueExtractor::class) {
	companion object {
		private val log = logger<EnvPropertyValueExtractor>()
		private const val ENV_FILE_NAME = ".env"
	}

	private val dotEnv = Dotenv
		.configure()
		.ignoreIfMissing()
		.load()

	override fun setProperties(): Map<Any, Any> {
		val file = File(ENV_FILE_NAME)
		if (!file.exists() || !envFileEnabled) {
			if (!envFileEnabled) {
				log.info(
					"Env file disabled. Skipping loading environment variables from {} file.",
					ENV_FILE_NAME,
				)
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

		log.info(
			"Extract: {} environment variables from: {} file: {}.",
			envFileKeys.size,
			ENV_FILE_NAME,
			envFileKeys,
		)
		return dotEnv.entries().associate { it.key to it.value }
	}

	override val extractionKey = "env"
}
