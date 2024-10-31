/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.i18n.source

import pl.jwizard.jwl.i18n.I18nLocaleSource

/**
 * Enum class representing various command sources for internationalization.
 *
 * @author Miłosz Gilga
 * @see I18nLocaleSource
 */
enum class I18nCommandSource(override val placeholder: String) : I18nLocaleSource {
	PLAY("jw.command.play"),
	PAUSE("jw.command.pause"),
	RESUME("jw.command.resume"),
	REPEAT("jw.command.repeat"),
	REPEATCLS("jw.command.repeatcls"),
	LOOP("jw.command.loop"),
	PLAYING("jw.command.playing"),
	PAUSED("jw.command.paused"),
	GETVOLUME("jw.command.getvolume"),
	QUEUE("jw.command.queue"),
	SETVOLUME("jw.command.setvolume"),
	VOLUMECLS("jw.command.volumecls"),
	JOIN("jw.command.join"),
	TRACKSRM("jw.command.tracksrm"),
	SHUFFLE("jw.command.shuffle"),
	SKIPTO("jw.command.skipto"),
	SKIP("jw.command.skip"),
	CLEAR("jw.command.clear"),
	STOP("jw.command.stop"),
	MOVE("jw.command.move"),
	INFINITE("jw.command.infinite"),
	ADDTRACKPL("jw.command.addtrackpl"),
	ADDQUEUEPL("jw.command.addqueuepl"),
	ADDPLAYLIST("jw.command.addplaylist"),
	PLAYPL("jw.command.playpl"),
	SHOWMEMPL("jw.command.showmempl"),
	SHOWMYPL("jw.command.showmypl"),
	SHOWPLTRACKS("jw.command.showplsongs"),
	VSHUFFLE("jw.command.vshuffle"),
	VSKIP("jw.command.vskip"),
	VSKIPTO("jw.command.vskipto"),
	VCLEAR("jw.command.vclear"),
	VSTOP("jw.command.vstop"),
	HELP("jw.command.help"),
	DEBUG("jw.command.debug"),
	SETTINGS("jw.command.settings"),
	PLAYRADIO("jw.command.radio"),
	STOPRADIO("jw.command.radiostop"),
	RADIOS("jw.command.radios"),
	RADIOINFO("jw.command.radioinfo"),
	COMMAND_DESCRIPTION_NOT_FOUND("jw.command.commandDescriptionNotFound"),
	;
}
