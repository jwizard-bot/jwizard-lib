/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.i18n

import org.apache.commons.vfs2.VFS
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.support.ResourceBundleMessageSource
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import pl.jwizard.jwl.SpringKtContextFactory
import pl.jwizard.jwl.persistence.sql.handler.LanguageSupplierBean
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
@Component
class I18nInitializerBean(private val environmentBean: BaseEnvironment) {

	companion object {
		private val log = logger<I18nInitializerBean>()
	}

	/**
	 * A map of languages loaded from the remote source, where the key is the language tag (ex. *en*) and the value
	 * is the language name.
	 */
	val languages = mutableMapOf<String, String>()

	/**
	 * Configures the [MessageSource] bean, setting up the required properties including base names, remote bundles,
	 * and encoding.
	 *
	 * @param languageSupplier A supplier that provides available languages.
	 * @return The configured [MessageSource] bean.
	 */
	@Bean
	fun messageSource(languageSupplier: LanguageSupplierBean): MessageSource {
		languages.putAll(languageSupplier.getLanguages())

		val libI18nSource = getMessageDirectories(ClassPathResource("/i18n-lib", SpringKtContextFactory::class.java))
		val classpathI18nSources = getMessageDirectories(ClassPathResource("i18n"))
		val sources = libI18nSource + classpathI18nSources
		log.info("Load: {} i18n message sources: {}.", sources.size, sources)

		val source = ResourceBundleMessageSource()
		source.setBasenames(*sources.toTypedArray())
		source.setDefaultEncoding(StandardCharsets.UTF_8.toString())
		return source
	}

	/**
	 * Retrieves the directories that contain message bundles for internationalization. It reads the specified
	 * [ClassPathResource] path and finds the associated message directories.
	 *
	 * @param resource The [ClassPathResource] that represents the base path for i18n files.
	 * @return A list of directory paths containing message bundles for each language.
	 */
	private fun getMessageDirectories(resource: ClassPathResource): List<String> {
		val directoryUri = VFS.getManager().resolveFile(resource.uri)
		return directoryUri.children.map { "${it.parent.name.baseName}/${it.name.baseName}/messages" }
	}
}
