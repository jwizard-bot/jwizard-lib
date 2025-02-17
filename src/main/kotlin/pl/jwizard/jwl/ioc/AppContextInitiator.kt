package pl.jwizard.jwl.ioc

import org.springframework.context.annotation.ComponentScan
import pl.jwizard.jwl.AppRunner

// put this annotation on root class (might be defined as object - singleton class)
@ComponentScan(basePackages = [AppRunner.BASE_PACKAGE])
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class AppContextInitiator
