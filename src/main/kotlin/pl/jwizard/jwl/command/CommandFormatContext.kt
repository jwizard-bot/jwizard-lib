/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.command

/**
 * Interface representing a command format used to process command in readable form.
 *
 * @author Miłosz Gilga
 */
interface CommandFormatContext {

	/**
	 * The prefix associated with the command.
	 */
	val prefix: String

	/**
	 * A boolean indicating whether the command is executed as a slash command event.
	 */
	val isSlashEvent: Boolean
}
