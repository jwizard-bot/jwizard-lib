package pl.jwizard.jwl.server.filter

import io.javalin.http.Context
import org.eclipse.jetty.http.HttpHeader
import pl.jwizard.jwl.property.AppBaseListProperty
import pl.jwizard.jwl.property.AppBaseProperty
import pl.jwizard.jwl.property.BaseEnvironment
import pl.jwizard.jwl.server.attribute.CommonServerAttribute
import pl.jwizard.jwl.server.definedHeader
import pl.jwizard.jwl.server.setAttribute

class LanguageHeaderExtractorFilter(environment: BaseEnvironment) : WebFilterBase {
	override val type = WebFilterType.BEFORE
	override val matcher = "/*"

	private val languages = environment.getListProperty<String>(AppBaseListProperty.I18N_LANGUAGES)
	private val defaultLanguage = environment
		.getProperty<String>(AppBaseProperty.I18N_DEFAULT_LANGUAGE)

	override fun filter(ctx: Context) {
		val langHeader = ctx.definedHeader(HttpHeader.ACCEPT_LANGUAGE)
		val language = languages.find { it == langHeader?.substring(0, 2) }
		ctx.setAttribute(CommonServerAttribute.I18N_LOCALE, language ?: defaultLanguage)
	}
}
