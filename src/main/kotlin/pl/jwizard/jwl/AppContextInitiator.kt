/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl

import org.springframework.context.annotation.ComponentScan

/**
 * Apply this annotation on main class, where you invoke `run` method from extended [AppRunner] class.
 *
 * @author Miłosz Gilga
 */
@ComponentScan(basePackages = [AppRunner.BASE_PACKAGE])
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class AppContextInitiator
