/*
 * Copyright (c) 2025 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.i18n

import org.springframework.context.MessageSource
import org.springframework.context.NoSuchMessageException
import pl.jwizard.jwl.ioc.stereotype.SingletonComponent
import pl.jwizard.jwl.property.AppBaseListProperty
import pl.jwizard.jwl.property.AppBaseProperty
import pl.jwizard.jwl.property.BaseEnvironment
import java.util.*

/**
 * A Spring Bean responsible for internationalization (i18n) of messages. It uses the [MessageSource] to fetch messages
 * based on locale and replaces placeholders with provided parameters.
 *
 * @property messageSource The [MessageSource] used to resolve messages.
 * @property environmentBean An [BaseEnvironment] that provides environment-specific properties with i18n configuration.
 * @property i18nInitializerBean The [I18nInitializerBean] component storing global configuration for i18n.
 * @author Miłosz Gilga
 */
@SingletonComponent
class I18nBean(
	private val messageSource: MessageSource,
	private val environmentBean: BaseEnvironment,
	private val i18nInitializerBean: I18nInitializerBean,
) {

	companion object {
		/**
		 * The delimiter used to mark the beginning of a placeholder in a message.
		 */
		private const val START_DELIMITER = "{{"

		/**
		 * The delimiter used to mark the end of a placeholder in a message.
		 */
		private const val END_DELIMITER = "}}"
	}

	/**
	 * The default language specified in the application properties.
	 */
	private val defaultLanguage = environmentBean.getProperty<String>(AppBaseProperty.I18N_DEFAULT_LANGUAGE)

	/**
	 * Retrieves a translated message based on the provided [I18nLocaleSource], language, and parameters.
	 *
	 * This method fetches a localized message for the specified locale. If no language is provided, it defaults
	 * to the language specified in the environment properties. Placeholders within the message are replaced with
	 * the corresponding values from the [args] map.
	 *
	 * @param i18nLocaleSource The source providing the placeholder for the message.
	 * @param lang The language tag representing the desired locale (ex. *en*). If null, the default language is used.
	 * @param args A map of parameters to replace placeholders within the message. Default is an empty map.
	 * @return The translated message with placeholders replaced by the corresponding parameters.
	 */
	fun t(i18nLocaleSource: I18nLocaleSource, lang: String?, args: Map<String, Any?> = emptyMap()): String {
		return tRaw(i18nLocaleSource.placeholder, args, lang)
	}

	/**
	 * Retrieves translated messages in all available languages for a given [I18nLocaleSource] and parameters.
	 *
	 * This method iterates through a list of supported languages defined in the environment properties. For each
	 * language, it fetches the localized message and replaces placeholders with values from the [args] map.
	 *
	 * @param i18nLocaleSource The source providing the placeholder for the message.
	 * @param args A map of parameters to replace placeholders within the message. Default is an empty map.
	 * @return A map where each key is a language code and the value is the translated message.
	 */
	fun t(i18nLocaleSource: I18nLocaleSource, args: Map<String, Any?> = emptyMap()): Map<String, String> {
		val languages = environmentBean.getListProperty<String>(AppBaseListProperty.I18N_LANGUAGES)
		return languages.associateWith { t(i18nLocaleSource, it, args) }
	}

	/**
	 * Retrieves a raw message based on the provided [I18nDynamicSource], arguments, and language.
	 *
	 * This method formats the key from the provided [I18nDynamicSource] using the provided [args], and then looks up the
	 * message in the specified language. It uses an empty map for parameters.
	 *
	 * @param i18NDynamicSource The [I18nDynamicSource] enum that provides the key pattern for the message.
	 * @param params Parameters to format the key pattern.
	 * @param args A map of parameters to replace placeholders within the message. Default is an empty map.
	 * @param lang The language tag representing the desired locale (ex. *en*). If null, the default language is used.
	 * @return The formatted localized message with placeholders replaced by the corresponding parameters.
	 */
	fun tRaw(i18NDynamicSource: I18nDynamicSource, params: Array<String?>, args: Map<String, Any?>, lang: String?) =
		tRaw(i18NDynamicSource.key.format(*params), args, lang)

	/**
	 * Retrieves a localized message for a dynamic module without additional placeholders.
	 *
	 * @param i18NDynamicSource The module providing the key pattern for the message.
	 * @param params Parameters used to format the key pattern.
	 * @param lang The language tag. Defaults to the configured default language.
	 * @return The resolved localized message.
	 */
	fun tRaw(i18NDynamicSource: I18nDynamicSource, params: Array<String?>, lang: String?) =
		tRaw(i18NDynamicSource.key.format(*params), emptyMap(), lang)

	/**
	 * Retrieves a raw message based on the provided i18n key, parameters, and language.
	 *
	 * This method fetches the message using the [MessageSource] for the given [i18nKey] and [lang]. It then replaces
	 * placeholders in the message with the provided parameters. If the key is not found or if there is an exception,
	 * it returns the [i18nKey] itself.
	 *
	 * @param i18nKey The key used to look up the message.
	 * @param args A map of parameters to replace placeholders in the message.
	 * @param lang The language tag representing the desired locale (ex. *en*). If null, the default language is used.
	 * @return The formatted localized message with placeholders replaced by the corresponding parameters.
	 */
	private fun tRaw(i18nKey: String, args: Map<String, Any?>, lang: String?): String {
		val locale = Locale.forLanguageTag(lang ?: defaultLanguage)
		return try {
			var propertyValue = messageSource.getMessage(i18nKey, null, locale)
			if (propertyValue.isBlank()) {
				propertyValue
			} else {
				for ((key, value) in args) {
					propertyValue = propertyValue.replace("$START_DELIMITER${key}$END_DELIMITER", value.toString())
				}
				propertyValue
			}
		} catch (ex: NoSuchMessageException) {
			i18nKey
		}
	}
}
