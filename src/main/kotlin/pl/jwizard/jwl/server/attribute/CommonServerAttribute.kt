/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.server.attribute

/**
 * Enum representing common server attributes used in the application. Each value in this enum corresponds to a
 * specific attribute ID that can be stored and accessed in the context of a server request. These attributes typically
 * represent data that is needed across different parts of the server handling process.
 *
 * @author Miłosz Gilga
 */
enum class CommonServerAttribute(override val attributeId: String) : ServerAttribute {

	/**
	 * The locale to be used for internationalization (i18n) in the current request context. This attribute is used to
	 * store the language or locale preference of the client.
	 */
	I18N_LOCALE("i18n_locale"),
	;
}
