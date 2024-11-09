/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.ioc.stereotype

import org.springframework.stereotype.Component

/**
 * Custom annotation to mark a class as a singleton component in the Spring IoC container.
 *
 * This annotation is used to automatically register the annotated class as a singleton Spring Bean with Spring's
 * [Component] functionality.
 *
 * Example usage:
 * ```kotlin
 * @SingletonComponent
 * class MySingletonService {
 *   fun doSomething() {
 *     // service logic here
 *   }
 * }
 * ```
 * @see Component for more information on Spring component scanning.
 * @author Miłosz Gilga
 */
@Component
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class SingletonComponent
