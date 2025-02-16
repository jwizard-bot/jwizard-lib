package pl.jwizard.jwl.file

import org.springframework.core.io.ClassPathResource
import java.nio.charset.StandardCharsets

class ClasspathFileLoader(location: String) {
	private val classPathResource = ClassPathResource(location)

	fun readFileRaw() = classPathResource.inputStream.bufferedReader(StandardCharsets.UTF_8)
		.use { it.readText() }
}
