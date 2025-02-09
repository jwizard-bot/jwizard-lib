/*
 * Copyright (c) 2025 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.command

/**
 * Represents the context format for commands, defining key properties such as command prefix and event type.
 *
 * @property prefix The prefix used for text-based commands.
 * @property isSlashEvent Indicates whether the command was triggered via a slash command event.
 * @author Miłosz Gilga
 */
class CommandFormatContextImpl(
	override val prefix: String,
	override val isSlashEvent: Boolean,
) : CommandFormatContext
