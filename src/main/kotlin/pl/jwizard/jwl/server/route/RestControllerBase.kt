package pl.jwizard.jwl.server.route

interface RestControllerBase {
	val basePath: String
	val routes: CompositedRoutes
}
