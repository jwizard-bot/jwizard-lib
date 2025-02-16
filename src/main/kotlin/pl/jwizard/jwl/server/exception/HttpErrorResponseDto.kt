package pl.jwizard.jwl.server.exception

data class HttpErrorResponseDto(
	val status: Int,
	val message: String,
	val details: Map<String, List<String>> = emptyMap(),
)
