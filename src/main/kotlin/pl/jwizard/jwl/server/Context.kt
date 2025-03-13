package pl.jwizard.jwl.server

import io.javalin.http.Context
import org.eclipse.jetty.http.HttpHeader

fun <T> Context.getAttribute(
	serverAttribute: ServerAttribute,
) = this.attribute<T>(serverAttribute.attributeId)

fun <T> Context.setAttribute(
	serverAttribute: ServerAttribute,
	value: T,
) = this.attribute(serverAttribute.attributeId, value)

fun Context.definedHeader(header: HttpHeader) = this.header(header.toString())
