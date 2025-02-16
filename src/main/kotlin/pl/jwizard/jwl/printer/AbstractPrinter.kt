package pl.jwizard.jwl.printer

import pl.jwizard.jwl.util.logger

abstract class AbstractPrinter(private val printer: Printer) {
	companion object {
		private val log = logger<AbstractPrinter>()

		@JvmStatic
		fun printContent(printers: Array<AbstractPrinter>) = printers.forEach { it.print() }
	}

	fun print() {
		setBodyContent()
			?.let { printer.print(it) }
			?: run {
				log.warn(
					"Unable to find content for {} printing statement.",
					this::class.simpleName,
				)
			}
	}

	protected abstract fun setBodyContent(): String?
}
