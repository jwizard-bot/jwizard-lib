/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.printer

import pl.jwizard.jwl.file.ClasspathFileLoader

/**
 * Class extending [AbstractPrinter] with [ConsolePrinter] as printer responsible for printing fancy
 * title taken by classpath config file.
 *
 * @property fileClasspathLocation Classpath fancy title file location.
 * @property printer Printer adapter implements [Printer] interface.
 * @author Miłosz Gilga
 * @see AbstractPrinter
 * @see Printer
 */
class FancyTitlePrinter(
	private val fileClasspathLocation: String,
	private val printer: Printer,
) : AbstractPrinter(printer) {

	override fun setBodyContent(): String {
		val loader = ClasspathFileLoader(fileClasspathLocation)
		return loader.readFileRaw()
	}
}
