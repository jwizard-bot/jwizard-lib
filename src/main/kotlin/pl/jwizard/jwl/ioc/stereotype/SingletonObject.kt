package pl.jwizard.jwl.ioc.stereotype

import org.springframework.context.annotation.Bean

@Bean
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class SingletonObject
