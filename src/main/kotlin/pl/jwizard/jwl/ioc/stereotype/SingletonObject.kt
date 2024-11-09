/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.ioc.stereotype

import org.springframework.context.annotation.Bean

/**
 * Custom annotation to mark a function as a singleton bean provider in the Spring IoC container.
 *
 * This annotation is used to create and manage a singleton instance of the returned object, similar to Spring's [Bean]
 * annotation.
 *
 * Example usage:
 * ```kotlin
 * class MyConfiguration {
 *   @SingletonObject
 *   fun mySingletonService(): MyService {
 *     return MyService()
 *   }
 * }
 * ```
 * @see Bean for more information on Spring's bean creation.
 * @author Miłosz Gilga
 */
@Bean
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class SingletonObject
