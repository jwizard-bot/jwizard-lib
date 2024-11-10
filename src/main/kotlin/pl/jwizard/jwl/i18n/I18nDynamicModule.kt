/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.i18n

import pl.jwizard.jwl.property.Property

/**
 * Defines a module interface for managing dynamic internationalization (i18n) properties.
 *
 * This interface allows for the configuration and retrieval of localized properties in a modular way, potentially for
 * different sections or modules within an application.
 *
 * @author Miłosz Gilga
 * @see Property
 */
interface I18nDynamicModule : Property
