package pl.jwizard.jwl.i18n

import org.apache.commons.vfs2.VFS
import org.springframework.context.MessageSource
import org.springframework.context.support.ResourceBundleMessageSource
import org.springframework.core.io.ClassPathResource
import pl.jwizard.jwl.util.logger
import java.io.IOException
import java.nio.charset.StandardCharsets

class I18nInitializer {
	companion object {
		private val log = logger<I18nInitializer>()
	}

	fun createMessageSource(): MessageSource {
		// load i18n content from library
		val libI18nSource = getI18nProjectDirectories("/i18n-lib")
		// load i18n content from current project
		val classpathI18nSources = getI18nProjectDirectories("/i18n")
		val sources = libI18nSource + classpathI18nSources
		log.info("Load: {} i18n message sources: {}.", sources.size, sources)

		val source = ResourceBundleMessageSource()
		source.setBasenames(*sources.toTypedArray())
		source.setDefaultEncoding(StandardCharsets.UTF_8.toString())
		return source
	}

	private fun getI18nProjectDirectories(path: String) = try {
		val resource = ClassPathResource(path)
		val directoryUri = VFS.getManager().resolveFile(resource.uri)
		directoryUri.children.map { "${it.parent.name.baseName}/${it.name.baseName}/messages" }
	} catch (ex: IOException) {
		log.error("Unable to list i18n directories from: \"{}\". Cause: {}.", path, ex.message)
		emptyList()
	}
}
