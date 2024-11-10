/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.radio

import pl.jwizard.jwl.DatabaseIdentifier
import pl.jwizard.jwl.command.ArgumentOption
import pl.jwizard.jwl.radio.StreamProvider.RMF_GROUP
import pl.jwizard.jwl.radio.StreamProvider.ZET_GROUP

/**
 * Enum representing different radio stations, including their metadata, stream URLs, and playback API URLs. Each
 * station is associated with its respective stream provider and database identifier.
 *
 * @property dbId The unique identifier for the radio station in the database.
 * @property placeholder The key used for referencing the radio station in the application (ex. for localization).
 * @property streamUrlFragments Fragments used to construct the stream URL for the radio station.
 * @property playbackApiFragments Fragments used to construct the API URL for the radio station's playback functionality.
 * @property streamProvider The [StreamProvider] associated with the radio station.
 * @author Miłosz Gilga
 */
enum class RadioStation(
	override val dbId: Long,
	override val placeholder: String,
	private val websiteUrlFragment: String,
	private val streamUrlFragments: Array<String>,
	private val playbackApiFragments: Array<String>,
	val streamProvider: StreamProvider,
) : DatabaseIdentifier, ArgumentOption {

	// rmf group
	RMF_FM(0, "jw.radio.rmfFm", "rmf.fm", arrayOf("101", "RMFFM48"), arrayOf("5"), RMF_GROUP),
	RMF_MAXX(1, "jw.radio.rmfMaxx", "rmfmaxx.pl", arrayOf("103", "MAXXXWAW"), arrayOf("213"), RMF_GROUP),

	// zet group
	ZET(2, "jw.radio.zet", "radiozet.pl", arrayOf("22533", "RADIO_ZETAAC"), arrayOf("radiozet"), ZET_GROUP),
	MELO(3, "jw.radio.melo", "meloradio.pl", arrayOf("27793", "MELORADIOAAC"), arrayOf("zetgold"), ZET_GROUP),
	ANTY(4, "jw.radio.anty", "antyradio.pl", arrayOf("25483", "ANTYRADIOAAC"), arrayOf("antyradio"), ZET_GROUP),
	;

	/**
	 * Constructs the stream URL for the radio station by formatting the base URL with the stream URL fragments.
	 */
	val streamUrl
		get() = streamProvider.baseUrlTemplate.format(*streamUrlFragments)

	/**
	 * Constructs the playback API URL for the radio station by formatting the playback API URL template with the
	 * playback API fragments.
	 */
	val playbackApiUrl
		get() = streamProvider.playbackProvider?.apiProviderTemplate?.format(*playbackApiFragments)

	/**
	 * Constructs the website URL for the radio station by prefixing the URL fragment with `https://`.
	 */
	val website
		get() = "https://$websiteUrlFragment"

	override val textKey
		get() = placeholder.substringAfterLast(".")
}
