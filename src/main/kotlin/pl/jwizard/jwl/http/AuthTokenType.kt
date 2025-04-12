package pl.jwizard.jwl.http

enum class AuthTokenType(val type: String) {
	BOT("Bot "),
	BEARER("Bearer "),
	TOKEN("token "),
	PLAIN(""),
	;
}
