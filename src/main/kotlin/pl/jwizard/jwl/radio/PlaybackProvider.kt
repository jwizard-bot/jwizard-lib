package pl.jwizard.jwl.radio

enum class PlaybackProvider(val apiProviderTemplate: String) {
	RMF_GROUP("https://www.rmfon.pl/stacje/playlista_%s.json.txt"),
	ZET_GROUP("https://rds.eurozet.pl/reader/var/%s.json?callback=rdsData"),
	;
}
