/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.server

import io.javalin.http.Context
import org.eclipse.jetty.http.HttpHeader
import pl.jwizard.jwl.server.attribute.ServerAttribute

/**
 * Retrieves an attribute from the request context based on the provided server attribute.
 *
 * @param T The type of the attribute to retrieve.
 * @param serverAttribute The server attribute containing the ID used to retrieve the value.
 * @return The attribute of type [T] if present; otherwise, `null`.
 */
fun <T> Context.getAttribute(serverAttribute: ServerAttribute) = this.attribute<T>(serverAttribute.attributeId)

/**
 * Sets an attribute in the request context with the specified server attribute and value.
 *
 * @param T The type of the attribute to set.
 * @param serverAttribute The server attribute containing the ID for the value.
 * @param value The value of the attribute to set in the context.
 */
fun <T> Context.setAttribute(serverAttribute: ServerAttribute, value: T) =
	this.attribute(serverAttribute.attributeId, value)

/**
 * Retrieves the value of a specific header from the request context.
 *
 * @param header The HTTP header to retrieve.
 * @return The header value as a string, or `null` if the header is not present.
 */
fun Context.definedHeader(header: HttpHeader) = this.header(header.toString())
