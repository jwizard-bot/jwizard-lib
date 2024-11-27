/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.i18n

import org.springframework.context.MessageSource
import org.springframework.context.support.ResourceBundleMessageSource
import pl.jwizard.jwl.file.IndependentFileBrowser
import pl.jwizard.jwl.ioc.stereotype.SingletonComponent
import pl.jwizard.jwl.ioc.stereotype.SingletonObject
import pl.jwizard.jwl.property.BaseEnvironment
import pl.jwizard.jwl.util.logger
import java.nio.charset.StandardCharsets

/**
 * Initializes internationalization (i18n) resources by setting up message source configurations and loading available
 * languages from external sources.
 *
 * @property environmentBean A Spring-managed bean that provides environment-specific properties used to configure the
 *           message source.
 * @author Miłosz Gilga
 */
@SingletonComponent
class I18nInitializerBean(private val environmentBean: BaseEnvironment) {

	companion object {
		private val log = logger<I18nInitializerBean>()
	}

	/**
	 * Configures the [MessageSource] bean, setting up the required properties including base names, remote bundles,
	 * and encoding.
	 *
	 * @return The configured [MessageSource] bean.
	 */
	@SingletonObject
	fun messageSource(): MessageSource {
		val libI18nSource = IndependentFileBrowser("/i18n-lib").getI18nProjectDirectories()
		val classpathI18nSources = IndependentFileBrowser("/i18n").getI18nProjectDirectories()
		val sources = libI18nSource + classpathI18nSources
		log.info("Load: {} i18n message sources: {}.", sources.size, sources)

		val source = ResourceBundleMessageSource()
		source.setBasenames(*sources.toTypedArray())
		source.setDefaultEncoding(StandardCharsets.UTF_8.toString())
		return source
	}
}
