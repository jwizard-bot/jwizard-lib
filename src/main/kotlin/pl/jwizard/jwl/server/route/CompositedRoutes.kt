package pl.jwizard.jwl.server.route

import io.javalin.http.Handler
import pl.jwizard.jwl.server.I18nExtHandler

class CompositedRoutes(
	val baseRoutes: Array<RouteDefinition<Handler>>,
	val routesWithI18n: Array<RouteDefinition<I18nExtHandler>>,
) {
	val size
		get() = baseRoutes.size + routesWithI18n.size
}
