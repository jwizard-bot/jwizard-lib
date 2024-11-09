/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.server

import pl.jwizard.jwl.ioc.IoCKtContextFactory

/**
 * Interface for handling server lifecycle events in the application. This interface provides a hook for executing
 * actions that should occur immediately after the server has started.
 *
 * Implementing classes should use the [afterStartServer] method to define startup logic that relies on access to the
 * application context.
 *
 * @author Miłosz Gilga
 */
interface HttpServerHook {

	/**
	 * Defines actions to be executed after the server has started.
	 *
	 * This method is typically used to initialize resources or configure components that need to be set up once the
	 * server is running.
	 *
	 * @param context The dependency injection context for accessing beans and other resources.
	 */
	fun afterStartServer(context: IoCKtContextFactory)
}
