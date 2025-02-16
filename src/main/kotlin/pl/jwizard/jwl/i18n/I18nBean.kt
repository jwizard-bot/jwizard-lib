package pl.jwizard.jwl.i18n

import org.springframework.context.MessageSource
import org.springframework.context.NoSuchMessageException
import pl.jwizard.jwl.ioc.stereotype.SingletonComponent
import pl.jwizard.jwl.property.AppBaseListProperty
import pl.jwizard.jwl.property.AppBaseProperty
import pl.jwizard.jwl.property.BaseEnvironment
import java.util.*

@SingletonComponent
class I18nBean(
	private val messageSource: MessageSource,
	private val environmentBean: BaseEnvironment,
) {
	companion object {
		private const val START_DELIMITER = "{{"
		private const val END_DELIMITER = "}}"
	}

	private val defaultLanguage = environmentBean
		.getProperty<String>(AppBaseProperty.I18N_DEFAULT_LANGUAGE)

	fun t(
		i18nLocaleSource: I18nLocaleSource,
		lang: String?,
		args: Map<String, Any?> = emptyMap(),
	) = tRaw(i18nLocaleSource.placeholder, args, lang)

	fun t(
		i18nLocaleSource: I18nLocaleSource,
		args: Map<String, Any?> = emptyMap(),
	) = environmentBean
		.getListProperty<String>(AppBaseListProperty.I18N_LANGUAGES)
		.associateWith { t(i18nLocaleSource, it, args) }

	fun tRaw(
		i18NDynamicSource: I18nDynamicSource,
		params: Array<String?>,
		args: Map<String, Any?>,
		lang: String?,
	) = tRaw(i18NDynamicSource.key.format(*params), args, lang)

	fun tRaw(
		i18NDynamicSource: I18nDynamicSource,
		params: Array<String?>,
		lang: String?,
	) = tRaw(i18NDynamicSource.key.format(*params), emptyMap(), lang)

	private fun tRaw(i18nKey: String, args: Map<String, Any?>, lang: String?): String {
		// get local from selected language tag, otherwise get default language tag
		val locale = Locale.forLanguageTag(lang ?: defaultLanguage)
		return try {
			var propertyValue = messageSource.getMessage(i18nKey, null, locale)
			if (propertyValue.isBlank()) {
				// if value is empty, return i18 key
				propertyValue
			} else {
				for ((key, value) in args) {
					// otherwise replace incrementally all values based on start and end delimiters
					propertyValue = propertyValue
						.replace("$START_DELIMITER${key}$END_DELIMITER", value.toString())
				}
				propertyValue
			}
		} catch (ex: NoSuchMessageException) {
			// if no i18n message found, return i18n key
			i18nKey
		}
	}
}
