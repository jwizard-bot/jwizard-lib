/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.ioc.stereotype

import org.springframework.stereotype.Service

/**
 * Custom annotation to mark a class as a singleton service in the Spring IoC container.
 *
 * This annotation registers the annotated class as a Spring [Service] bean with singleton scope, making it ideal for
 * classes that provide core business logic.
 *
 * Example usage:
 * ```kotlin
 * @SingletonService
 * class MyBusinessService {
 *   fun performAction() {
 *     // business logic here
 *   }
 * }
 * ```
 * @see Service for more details on Spring service components.
 * @author Miłosz Gilga
 */
@Service
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class SingletonService
