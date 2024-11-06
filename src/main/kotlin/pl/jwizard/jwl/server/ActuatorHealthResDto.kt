/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.server

/**
 * Data transfer object representing the health status of the application, used in health checks.
 *
 * @property status The current health status of the application, typically represented as "UP" or "DOWN".
 * @author Miłosz Gilga
 */
data class ActuatorHealthResDto(val status: String)
