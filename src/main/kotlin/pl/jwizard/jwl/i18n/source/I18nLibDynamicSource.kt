/*
 * Copyright (c) 2025 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.i18n.source

import pl.jwizard.jwl.i18n.I18nDynamicSource
import pl.jwizard.jwl.i18n.source.I18nLibDynamicSource.PROJECT_DESCRIPTION
import pl.jwizard.jwl.i18n.source.I18nLibDynamicSource.PROJECT_NAME

/**
 * TODO
 *
 * Defining following properties:
 * - [PROJECT_NAME]: TODO
 * - [PROJECT_DESCRIPTION]: TODO
 *
 * @property key The key string used for retrieving the localized value.
 * @author Miłosz Gilga
 */
enum class I18nLibDynamicSource(override val key: String) : I18nDynamicSource {

	/**
	 * TODO
	 */
	PROJECT_NAME("jw.project.name.%s"),

	/**
	 * TODO
	 */
	PROJECT_DESCRIPTION("jw.project.description.%s"),
	;
}

