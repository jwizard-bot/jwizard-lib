/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.ioc.stereotype

import org.springframework.stereotype.Controller

/**
 * Custom annotation to mark a class as a singleton controller in the Spring IoC container.
 *
 * This annotation registers the annotated class as a Spring [Controller] bean with singleton scope.
 *
 * Example usage:
 * ```kotlin
 * @SingletonController
 * class MySingletonController {
 *   fun handleRequest() {
 *     // controller logic here
 *   }
 * }
 * ```
 * @see Controller for more information on Spring's MVC controller mechanism.
 * @author Miłosz Gilga
 */
@Controller
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class SingletonController
