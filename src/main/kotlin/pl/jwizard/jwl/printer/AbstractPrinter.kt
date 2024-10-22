/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.printer

import pl.jwizard.jwl.util.logger

/**
 * Abstract class defining custom content printer (ex. console or log statement printer).
 *
 * @property printer Printer class definition.
 * @author Miłosz Gilga
 * @see Printer
 */
abstract class AbstractPrinter(private val printer: Printer) {

	companion object {
		private val log = logger<AbstractPrinter>()

		/**
		 * Prints content using an array of [AbstractPrinter] instances.
		 *
		 * @param printers An array of [AbstractPrinter] instances to use for printing.
		 */
		@JvmStatic
		fun printContent(printers: Array<AbstractPrinter>) = printers.forEach { it.print() }
	}

	/**
	 * Method responsible for printing custom content taking from [print] method by declared [printer]
	 * class.
	 */
	fun print() {
		setBodyContent()
			?.let { printer.print(it) }
			?: run { log.warn("Unable to find content for {} printing statement.", this::class.simpleName) }
	}

	/**
	 * Method responsible for setting body content printing by [print] method.
	 *
	 * @return Body content passing to [print] method; if body content is null, printing is disabled.
	 * @author Miłosz Gilga
	 */
	protected abstract fun setBodyContent(): String?
}
