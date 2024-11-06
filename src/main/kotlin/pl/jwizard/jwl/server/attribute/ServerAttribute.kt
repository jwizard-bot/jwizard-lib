/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.server.attribute

/**
 * Interface representing a server attribute. It defines a contract for any object that can serve as an attribute in
 * the context of a web request. The attribute is identified by a unique `attributeId` which is used to store and
 * retrieve the attribute.
 *
 * @author Miłosz Gilga
 */
interface ServerAttribute {

	/**
	 * The unique identifier for the server attribute. This ID is used to store and retrieve the attribute from the
	 * request or session context.
	 */
	val attributeId: String
}
