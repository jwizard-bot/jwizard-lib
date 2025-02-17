package pl.jwizard.jwl.printer

import org.springframework.core.io.ClassPathResource
import java.nio.charset.StandardCharsets

internal class FancyTitlePrinter(
	fileClasspathLocation: String,
	printer: Printer,
) : AbstractPrinter(printer) {
	private val classPathResource = ClassPathResource(fileClasspathLocation)

	override fun setBodyContent() = classPathResource.inputStream
		.bufferedReader(StandardCharsets.UTF_8)
		.use { it.readText() }
}
