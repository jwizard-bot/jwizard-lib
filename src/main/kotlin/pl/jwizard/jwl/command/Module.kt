package pl.jwizard.jwl.command

import pl.jwizard.jwl.DatabaseIdentifier
import pl.jwizard.jwl.TextKeyExtractor
import pl.jwizard.jwl.i18n.I18nLocaleSource

enum class Module(
	override val dbId: Long,
	override val placeholder: String,
) : DatabaseIdentifier, I18nLocaleSource, TextKeyExtractor {
	MUSIC(0, "jw.module.music"),
	DJ(100, "jw.module.dj"),
	PLAYLIST(200, "jw.module.playlist"),
	VOTE(300, "jw.module.vote"),
	OTHER(400, "jw.module.other"),
	RADIO(500, "jw.module.radio"),
	;

	override val textKey
		get() = placeholder.replace("jw.module.", "")
}
