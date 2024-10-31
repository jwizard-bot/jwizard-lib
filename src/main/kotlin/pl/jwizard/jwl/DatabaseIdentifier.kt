/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl

/**
 * An interface that represents an entity with a unique database identifier. Any class implementing this interface
 * should provide a way to retrieve its database ID.
 *
 * @author Miłosz Gilga
 */
interface DatabaseIdentifier {

	/**
	 * The unique identifier for the entity in the database.
	 */
	val dbId: Long
}
