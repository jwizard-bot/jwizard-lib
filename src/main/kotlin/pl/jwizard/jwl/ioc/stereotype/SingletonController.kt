package pl.jwizard.jwl.ioc.stereotype

import org.springframework.stereotype.Controller

@Controller
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class SingletonController
