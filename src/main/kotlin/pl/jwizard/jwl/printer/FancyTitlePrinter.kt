package pl.jwizard.jwl.printer

import pl.jwizard.jwl.file.ClassPathFileResource

internal class FancyTitlePrinter(
	fileClasspathLocation: String,
	printer: Printer,
) : AbstractPrinter(printer) {
	private val classPathResource = ClassPathFileResource(fileClasspathLocation)

	override fun setBodyContent() = classPathResource.getRawContent()
}
