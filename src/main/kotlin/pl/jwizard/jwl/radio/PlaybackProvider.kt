/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.radio

/**
 * Enum representing different playback providers, each associated with a specific API URL template.
 *
 * This enum defines different playback providers used for streaming data and retrieving playlists in various formats
 * (such as JSON). Each provider is associated with an API URL template that can be formatted with specific parameters
 * to fetch the appropriate playlist or stream data.
 *
 * @property apiProviderTemplate The URL template for the API endpoint used to retrieve playback data.
 * @author Miłosz Gilga
 */
enum class PlaybackProvider(val apiProviderTemplate: String) {
	RMF_GROUP("https://www.rmfon.pl/stacje/playlista_%s.json.txt"),
	ZET_GROUP("https://rds.eurozet.pl/reader/var/%s.json?callback=rdsData"),
	;
}
