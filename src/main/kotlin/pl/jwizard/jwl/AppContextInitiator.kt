package pl.jwizard.jwl

import org.springframework.context.annotation.ComponentScan

// put this annotation on root class (might be object - singleton class)
@ComponentScan(basePackages = [AppRunner.BASE_PACKAGE])
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class AppContextInitiator
