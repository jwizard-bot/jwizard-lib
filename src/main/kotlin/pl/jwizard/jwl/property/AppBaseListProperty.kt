/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.property

import pl.jwizard.jwl.property.AppBaseListProperty.*
import kotlin.reflect.KClass

/**
 * Enum representing different types of list-properties used in the application.
 *
 * Defining following properties:
 * - [RUNTIME_PROFILES]: Application runtime profiles. Defined all others configurations and Spring Context loaders.
 *   Accepted: *dev*, *prod*. Default: *empty array*.
 * - [JDA_PERMISSIONS]: Permissions required by the JDA (Java Discord API) library for accessing and interacting with
 *   Discord.
 * - [I18N_LANGUAGES]: The placeholder for the available languages in internationalization.
 *
 * @property key The key used to retrieve the property value.
 * @property listElementsType The type of elements in the list represented by this property.
 * @property separator Optional separator used to split the list elements in a string representation.
 * @author Miłosz Gilga
 */
enum class AppBaseListProperty(
	override val key: String,
	override val separator: String? = null,
	override val listElementsType: KClass<*> = String::class,
) : AppListProperty {

	/**
	 * Application runtime profiles. Defined all others configurations and Spring Context loaders.
	 * Accepted: *dev*, *prod*. Default: *empty array*.
	 */
	RUNTIME_PROFILES("runtime.profiles", ","),

	/**
	 * Permissions required by the JDA (Java Discord API) library for accessing and interacting with Discord.
	 */
	JDA_PERMISSIONS("jda.permissions"),

	/**
	 * The placeholder for the available languages in internationalization.
	 */
	I18N_LANGUAGES("i18n.languages"),
	;
}
