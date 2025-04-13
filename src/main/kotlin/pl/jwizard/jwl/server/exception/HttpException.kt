package pl.jwizard.jwl.server.exception

import io.javalin.http.HttpResponseException

class HttpException(base: ExceptionBase) : HttpResponseException(base.httpStatus, base.key)
