/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.ioc

import org.springframework.beans.factory.DisposableBean

/**
 * Interface for beans requiring custom cleanup logic upon IoC container shutdown.
 *
 * Classes implementing this interface should define cleanup actions to be executed when the Spring IoC container is
 * destroyed, using the [destroy] method from [DisposableBean].
 *
 * Example usage:
 * ```kotlin
 * class MyResourceHandler : CleanupAfterIoCDestroy {
 *   override fun destroy() {
 *     // custom cleanup logic here
 *   }
 * }
 * ```
 * @see DisposableBean for more details on Spring bean destruction lifecycle.
 * @author Miłosz Gilga
 */
interface CleanupAfterIoCDestroy : DisposableBean
