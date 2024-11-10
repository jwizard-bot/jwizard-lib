/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.server.filter

import io.javalin.http.Context
import org.eclipse.jetty.http.HttpHeader
import pl.jwizard.jwl.ioc.stereotype.SingletonComponent
import pl.jwizard.jwl.property.AppBaseListProperty
import pl.jwizard.jwl.property.AppBaseProperty
import pl.jwizard.jwl.property.BaseEnvironment
import pl.jwizard.jwl.server.attribute.CommonServerAttribute
import pl.jwizard.jwl.server.definedHeader
import pl.jwizard.jwl.server.setAttribute

/**
 * A filter that extracts the preferred language from the `Accept-Language` header of the incoming HTTP request
 * and sets it as a locale attribute in the context. If the preferred language is not supported, it uses a default
 * language.
 *
 * @property environment An instance of [BaseEnvironment] used to retrieve application properties.
 * @author Miłosz Gilga
 */
@SingletonComponent
class LanguageHeaderExtractorFilter(private val environment: BaseEnvironment) : WebFilterBase {

	override val type = WebFilterType.BEFORE
	override val matcher = "/*"

	/**
	 * A list of supported languages fetched from the environment properties.
	 */
	private val languages = environment.getListProperty<String>(AppBaseListProperty.I18N_LANGUAGES)

	/**
	 * The default language that will be used if the preferred language is not found in the request header.
	 */
	private val defaultLanguage = environment.getProperty<String>(AppBaseProperty.I18N_DEFAULT_LANGUAGE)

	/**
	 * Filters incoming requests by checking the `Accept-Language` header to determine the preferred language of the user.
	 *
	 * If the language is valid (ex. it matches one of the supported languages), it sets that language as the locale in
	 * the context. Otherwise, it sets the default language.
	 *
	 * @param ctx The [Context] object representing the HTTP request and response.
	 */
	override fun filter(ctx: Context) {
		val langHeader = ctx.definedHeader(HttpHeader.ACCEPT_LANGUAGE)
		val language = languages.find { it == langHeader?.substring(0, 2) }
		ctx.setAttribute(CommonServerAttribute.I18N_LOCALE, language ?: defaultLanguage)
	}
}
