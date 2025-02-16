package pl.jwizard.jwl.radio

import pl.jwizard.jwl.DatabaseIdentifier
import pl.jwizard.jwl.command.ArgumentOption
import pl.jwizard.jwl.radio.StreamProvider.RMF_GROUP
import pl.jwizard.jwl.radio.StreamProvider.ZET_GROUP

enum class RadioStation(
	override val dbId: Long,
	override val placeholder: String,
	private val websiteUrlFragment: String,
	private val streamUrlFragments: Array<String>,
	private val playbackApiFragments: Array<String>,
	val streamProvider: StreamProvider,
) : DatabaseIdentifier, ArgumentOption {
	// @formatter:off

	// rmf group
	RMF_FM(0, "jw.radio.rmfFm", "rmf.fm", arrayOf("101", "RMFFM48"), arrayOf("5"), RMF_GROUP),
	RMF_MAXX(1, "jw.radio.rmfMaxx", "rmfmaxx.pl", arrayOf("103", "MAXXXWAW"), arrayOf("213"), RMF_GROUP),

	// zet group
	ZET(2, "jw.radio.zet", "radiozet.pl", arrayOf("22533", "RADIO_ZETAAC"), arrayOf("radiozet"), ZET_GROUP),
	MELO(3, "jw.radio.melo", "meloradio.pl", arrayOf("27793", "MELORADIOAAC"), arrayOf("zetgold"), ZET_GROUP),
	ANTY(4, "jw.radio.anty", "antyradio.pl", arrayOf("25483", "ANTYRADIOAAC"), arrayOf("antyradio"), ZET_GROUP),
	;

	// @formatter:on

	val streamUrl
		get() = streamProvider.baseUrlTemplate.format(*streamUrlFragments)

	val playbackApiUrl
		get() = streamProvider.playbackProvider?.apiProviderTemplate?.format(*playbackApiFragments)

	val website
		get() = "https://$websiteUrlFragment"

	override val textKey
		get() = placeholder.substringAfterLast(".")
}
