package pl.jwizard.jwl.printer

import pl.jwizard.jwl.file.ClasspathFileLoader

class FancyTitlePrinter(
	private val fileClasspathLocation: String,
	printer: Printer,
) : AbstractPrinter(printer) {

	override fun setBodyContent(): String {
		val loader = ClasspathFileLoader(fileClasspathLocation)
		return loader.readFileRaw()
	}
}
