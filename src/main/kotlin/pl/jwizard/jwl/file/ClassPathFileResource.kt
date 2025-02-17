package pl.jwizard.jwl.file

import org.springframework.core.io.ClassPathResource
import java.nio.charset.StandardCharsets

class ClassPathFileResource(resourcePath: String) {
	private val classPathResource = ClassPathResource(resourcePath)

	fun getRawContent() = classPathResource.inputStream
		.bufferedReader(StandardCharsets.UTF_8)
		.use { it.readText() }
}
