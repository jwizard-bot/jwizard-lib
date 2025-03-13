package pl.jwizard.jwl.server.route

import pl.jwizard.jwl.server.route.handler.Handler

interface HttpControllerBase {
	val basePath: String
	val routes: List<RouteDefinition<Handler>>
}
