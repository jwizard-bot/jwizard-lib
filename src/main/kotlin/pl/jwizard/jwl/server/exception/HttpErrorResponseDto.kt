/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.server.exception

/**
 * A data class representing an HTTP error response DTO (Data Transfer Object).
 *
 * This class is used as a DTO to transfer error details such as the HTTP status code and message between layers in
 * the application or when sending error responses to clients.
 *
 * @property status The HTTP status code associated with the error.
 * @property message A descriptive message explaining the error.
 * @property details Additional exception response details as key list value elements.
 * @author Miłosz Gilga
 */
data class HttpErrorResponseDto(
	val status: Int,
	val message: String,
	val details: Map<String, List<String>> = emptyMap(),
)
