package pl.jwizard.jwl.ioc.stereotype

import org.springframework.stereotype.Component

@Component
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class SingletonComponent
