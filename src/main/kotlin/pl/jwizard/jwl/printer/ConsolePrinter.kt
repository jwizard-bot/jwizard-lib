package pl.jwizard.jwl.printer

internal class ConsolePrinter : Printer {
	override fun print(content: String) {
		println(content)
	}
}
