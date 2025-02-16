package pl.jwizard.jwl.command

interface CommandFormatContext {
	val prefix: String

	val isSlashEvent: Boolean
}
