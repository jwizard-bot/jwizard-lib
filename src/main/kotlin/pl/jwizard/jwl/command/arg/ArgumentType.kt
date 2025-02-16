package pl.jwizard.jwl.command.arg

import pl.jwizard.jwl.i18n.I18nLocaleSource

enum class ArgumentType(
	override val placeholder: String,
	val castTo: (value: String) -> Any,
) : I18nLocaleSource {
	STRING("jw.type.string", { it }),
	INTEGER("jw.type.int", { it.toInt() }),
	MENTIONABLE(
		"jw.type.mentionable",
		{ (if (it.contains("@")) it.replace(Regex("<@|>"), "") else it).toLong() },
	),
	CHANNEL(
		"jw.type.channel",
		{ (if (it.contains("#")) it.replace(Regex("<#|>"), "") else it).toLong() }),
	BOOLEAN("jw.type.boolean", { it.toBoolean() }),
	;
}
