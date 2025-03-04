package pl.jwizard.jwl.server.route

interface HttpControllerBase {
	val basePath: String
	val routes: CompositedRoutes
}
