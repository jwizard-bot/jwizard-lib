/*
 * Copyright (c) 2025 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.i18n.source

import pl.jwizard.jwl.i18n.I18nDynamicSource
import pl.jwizard.jwl.i18n.source.I18nLibDynamicSource.PROJECT_DESCRIPTION
import pl.jwizard.jwl.i18n.source.I18nLibDynamicSource.PROJECT_NAME

/**
 * Enum defining dynamic keys for internationalization (i18n) used to retrieve localized strings related to project
 * details such as name and description.

 *
 * Defining following properties:
 * - [PROJECT_NAME]: Key used to retrieve the localized project name.
 * - [PROJECT_DESCRIPTION]: Key used to retrieve the localized project description.
 *
 * @property key The key string used for retrieving the localized value.
 * @author Miłosz Gilga
 */
enum class I18nLibDynamicSource(override val key: String) : I18nDynamicSource {

	/**
	 * Key used to retrieve the localized project name.
	 * The format of the key is `jw.project.name.%s`, where `%s` will be replaced with the project slug.
	 */
	PROJECT_NAME("jw.project.name.%s"),

	/**
	 * Key used to retrieve the localized project description.
	 * The format of the key is `jw.project.description.%s`, where `%s` will be replaced with the project slug.
	 */
	PROJECT_DESCRIPTION("jw.project.description.%s"),
	;
}

