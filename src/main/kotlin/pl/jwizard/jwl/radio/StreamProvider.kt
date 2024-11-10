/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.radio

/**
 * Enum representing different stream providers, each associated with a stream URL template and an optional playback
 * provider.
 *
 * This enum defines different stream providers that supply the base URL templates used for streaming audio content.
 * Each provider is associated with a specific URL template for constructing the streaming URLs. Additionally, some
 * stream providers are linked with a [PlaybackProvider], which provides the corresponding API template for fetching
 * playback data.
 *
 * @property baseUrlTemplate The URL template for constructing the stream URL.
 * @property playbackProvider An optional [PlaybackProvider] that provides an API URL template for retrieving playback
 *           information for the stream. If not specified, this value will be `null`.
 * @author Miłosz Gilga
 */
enum class StreamProvider(
	val baseUrlTemplate: String,
	val playbackProvider: PlaybackProvider? = null,
) {
	RMF_GROUP("https://rs%s-krk-cyfronet.rmfstream.pl/%s", PlaybackProvider.RMF_GROUP),
	ZET_GROUP("https://%s.live.streamtheworld.com/%s.aac", PlaybackProvider.ZET_GROUP),
	;
}
