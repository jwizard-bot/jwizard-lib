package pl.jwizard.jwl

import pl.jwizard.jwl.ioc.IoCKtContextFactory
import pl.jwizard.jwl.printer.AbstractPrinter
import pl.jwizard.jwl.printer.ConsolePrinter
import pl.jwizard.jwl.printer.FancyFramePrinter
import pl.jwizard.jwl.printer.FancyTitlePrinter
import pl.jwizard.jwl.util.logger
import kotlin.reflect.KClass

abstract class AppRunner {
	companion object {
		private val log = logger<AppRunner>()

		// used for scanning IoC components
		const val BASE_PACKAGE = "pl.jwizard"

		// directory, where banner and fancy title are located
		private const val PRINTABLE_DATA_DIRECTORY = "util"
	}

	private lateinit var context: IoCKtContextFactory

	fun run(clazz: KClass<*>) {
		val startTimestamp = System.currentTimeMillis()
		val printer = ConsolePrinter()
		val printers = arrayOf(
			FancyTitlePrinter("$PRINTABLE_DATA_DIRECTORY/banner.txt", printer),
			FancyFramePrinter("$PRINTABLE_DATA_DIRECTORY/frame.txt", printer),
		)
		AbstractPrinter.printContent(printers)
		try {
			try {
				context = IoCKtContextFactory(clazz)
				log.info(
					"Init IoC Context with base class: {}. Init packages tree: {}.",
					clazz.qualifiedName,
					BASE_PACKAGE,
				)
				runWithContext(context)

				val endTimestamp = System.currentTimeMillis()
				val elapsedTime = endTimestamp - startTimestamp
				log.info("Load in: {}s. Start listening incoming requests...", elapsedTime / 1000.0)
			} catch (throwable: Throwable) {
				throw IrreparableException(throwable)
			}
		} catch (ex: IrreparableException) {
			ex.printLogStatement()
			ex.killProcess()
		}
	}

	protected abstract fun runWithContext(context: IoCKtContextFactory)
}
