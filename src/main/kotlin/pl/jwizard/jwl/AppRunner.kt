/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl

import org.springframework.context.annotation.ComponentScan
import pl.jwizard.jwl.printer.AbstractPrinter
import pl.jwizard.jwl.printer.ConsolePrinter
import pl.jwizard.jwl.printer.FancyFramePrinter
import pl.jwizard.jwl.printer.FancyTitlePrinter
import pl.jwizard.jwl.util.logger
import kotlin.reflect.KClass

/**
 * Abstract class responsible for running the application. It initializes the Spring context, handles printing fancy
 * banners and frames at startup, and runs the main logic of the application.
 *
 * @author Miłosz Gilga
 */
abstract class AppRunner {
	private val log = logger<AppRunner>()

	companion object {
		/**
		 * Base application package. Used for Spring Context [ComponentScan] annotation. All classes related with
		 * Spring IoC containers will be loaded into Spring Context.
		 */
		const val BASE_PACKAGE = "pl.jwizard"

		/**
		 * Directory path where printable data like banners and frames are stored. This data is used for rendering fancy
		 * console outputs during application startup.
		 */
		private const val PRINTABLE_DATA_DIRECTORY = "util"
	}

	/**
	 * Kotlin IoC context container instance.
	 */
	private lateinit var context: IoCKtContextFactory

	/**
	 * Runs the application with the given class as the base configuration class for the Spring context. This method
	 * initializes the context, prints banners and frames to the console, and handles exceptions that may occur during
	 * the initialization process.
	 *
	 * @param clazz The base class used to initialize the Spring context.
	 */
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
				log.info("Init IoC Context with base class: {}. Init packages tree: {}.", clazz.qualifiedName, BASE_PACKAGE)

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

	/**
	 * Abstract method that must be implemented by subclasses. It defines the logic that should run after the Spring
	 * context has been initialized.
	 *
	 * @param context The initialized Spring Kotlin context.
	 */
	protected abstract fun runWithContext(context: IoCKtContextFactory)
}
