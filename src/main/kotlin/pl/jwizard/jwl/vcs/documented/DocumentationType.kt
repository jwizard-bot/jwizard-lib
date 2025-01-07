/*
 * Copyright (c) 2025 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.vcs.documented

import pl.jwizard.jwl.i18n.I18nLocaleSource

/**
 * Enum representing types of documentation that can be generated or linked.
 *
 * @property placeholder The placeholder key used for internationalized labels.
 * @property typeName The name of the documentation type (used in URLs or display).
 * @author Miłosz Gilga
 */
enum class DocumentationType(
	override val placeholder: String,
	val typeName: String,
) : I18nLocaleSource {
	KDOC("jw.documentation.kdoc", "kdoc"),
	JAVADOC("jw.documentation.javadoc", "javadoc"),
	;
}
