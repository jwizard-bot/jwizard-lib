package pl.jwizard.jwl.i18n

import org.springframework.context.MessageSource
import org.springframework.context.support.ResourceBundleMessageSource
import pl.jwizard.jwl.file.IndependentFileBrowser
import pl.jwizard.jwl.ioc.stereotype.SingletonComponent
import pl.jwizard.jwl.ioc.stereotype.SingletonObject
import pl.jwizard.jwl.util.logger
import java.nio.charset.StandardCharsets

@SingletonComponent
class I18nInitializerBean {
	companion object {
		private val log = logger<I18nInitializerBean>()
	}

	@SingletonObject
	fun messageSource(): MessageSource {
		// load i18n content from library
		val libI18nSource = IndependentFileBrowser("/i18n-lib").getI18nProjectDirectories()
		// load i18n content from current project
		val classpathI18nSources = IndependentFileBrowser("/i18n").getI18nProjectDirectories()
		val sources = libI18nSource + classpathI18nSources
		log.info("Load: {} i18n message sources: {}.", sources.size, sources)

		val source = ResourceBundleMessageSource()
		source.setBasenames(*sources.toTypedArray())
		source.setDefaultEncoding(StandardCharsets.UTF_8.toString())
		return source
	}
}
