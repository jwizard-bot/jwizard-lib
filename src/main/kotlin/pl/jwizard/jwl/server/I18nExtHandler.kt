/*
 * Copyright (c) 2025 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.server

import io.javalin.http.Context

/**
 * Interface for handling internationalization (i18n) extensions. Provides a method to process localization based on
 * the provided language parameter.
 *
 * @author Miłosz Gilga
 */
fun interface I18nExtHandler {

	/**
	 * Handles the localization process based on the provided language.
	 *
	 * @param ctx The Javalin HTTP context containing request and response data.
	 * @param language The optional language code (ex. "en", "pl") to determine localization.
	 */
	fun handle(ctx: Context, language: String?)
}
