package pl.jwizard.jwl.server.exception

import io.javalin.http.HttpStatus

interface ExceptionBase {
	val key: String
	val httpStatus: HttpStatus
}
