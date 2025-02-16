package pl.jwizard.jwl.radio

enum class StreamProvider(
	val baseUrlTemplate: String,
	val playbackProvider: PlaybackProvider? = null,
) {
	RMF_GROUP("https://rs%s-krk-cyfronet.rmfstream.pl/%s", PlaybackProvider.RMF_GROUP),
	ZET_GROUP("https://%s.live.streamtheworld.com/%s.aac", PlaybackProvider.ZET_GROUP),
	;
}
