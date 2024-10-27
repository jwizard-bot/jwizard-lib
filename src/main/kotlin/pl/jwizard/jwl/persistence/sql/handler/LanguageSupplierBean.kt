/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.persistence.sql.handler

import org.springframework.stereotype.Component
import pl.jwizard.jwl.persistence.sql.ColumnDef
import pl.jwizard.jwl.persistence.sql.JdbcKtTemplateBean
import pl.jwizard.jwl.property.AppBaseProperty
import pl.jwizard.jwl.property.BaseEnvironment
import java.math.BigInteger

/**
 * Component responsible for retrieves language data from a database. It uses [JdbcKtTemplateBean] to query for
 * supported languages and guild-specific languages.
 *
 * @property jdbcKtTemplateBean The [JdbcKtTemplateBean] bean used for database operations.
 * @property environmentBean Provides environment-specific properties, such as the default language.
 * @author Miłosz Gilga
 */
@Component
class LanguageSupplierBean(
	private val jdbcKtTemplateBean: JdbcKtTemplateBean,
	private val environmentBean: BaseEnvironment,
) {

	/**
	 * Retrieves a map of all supported languages from the database.
	 *
	 * @return A map of language tag (keys) and names (values) retrieved from the `languages` table.
	 */
	fun getLanguages() = jdbcKtTemplateBean.queryForListMap(
		sql = "SELECT tag, name FROM languages",
		key = ColumnDef("tag", String::class),
		value = ColumnDef("name", String::class)
	)

	/**
	 * Retrieves the ID of a language based on its tag. If the language tag is not found, it attempts to retrieve the ID
	 * of the default language specified in the environment properties. If the default language is also not found, it
	 * returns `null`.
	 *
	 * @param tag The language tag for which the ID is to be retrieved.
	 * @return The ID of the language corresponding to the provided tag. If the tag is not found, it returns the ID of
	 *         the default language or `null`.
	 */
	fun getLanguageId(tag: String): BigInteger? {
		val sql = "SELECT id FROM languages WHERE tag = ?"
		val language = jdbcKtTemplateBean.queryForNullableObject(sql, BigInteger::class, tag)
		if (language == null) {
			val configLanguage = environmentBean.getProperty<String>(AppBaseProperty.I18N_DEFAULT_LANGUAGE)
			return jdbcKtTemplateBean.queryForNullableObject(sql, BigInteger::class, configLanguage)
		}
		return language
	}
}
