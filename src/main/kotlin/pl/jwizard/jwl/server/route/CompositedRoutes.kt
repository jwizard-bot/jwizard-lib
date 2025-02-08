/*
 * Copyright (c) 2025 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.server.route

import io.javalin.http.Handler
import pl.jwizard.jwl.server.I18nExtHandler

/**
 * Represents a collection of route definitions, including standard routes and routes that support internationalization
 * (i18n).
 *
 * @property baseRoutes An array of route definitions that use standard handlers.
 * @property routesWithI18n An array of route definitions that use i18n handlers.
 * @author Miłosz Gilga
 */
class CompositedRoutes(
	val baseRoutes: Array<RouteDefinition<Handler>>,
	val routesWithI18n: Array<RouteDefinition<I18nExtHandler>>,
) {

	/**
	 * Computes the total number of routes by summing both base and i18n-specific routes.
	 *
	 * @return The total count of routes.
	 */
	val size
		get() = baseRoutes.size + routesWithI18n.size
}
