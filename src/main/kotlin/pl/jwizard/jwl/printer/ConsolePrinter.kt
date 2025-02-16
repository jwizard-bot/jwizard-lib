package pl.jwizard.jwl.printer

class ConsolePrinter : Printer {
	override fun print(content: String) {
		println(content)
	}
}
