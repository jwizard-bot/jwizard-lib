package pl.jwizard.jwl.server

import pl.jwizard.jwl.ioc.IoCKtContextFactory

interface HttpServerHook {
	fun afterStartServer(context: IoCKtContextFactory)
}
