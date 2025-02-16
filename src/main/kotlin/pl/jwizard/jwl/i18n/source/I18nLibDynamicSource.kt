package pl.jwizard.jwl.i18n.source

import pl.jwizard.jwl.i18n.I18nDynamicSource

enum class I18nLibDynamicSource(override val key: String) : I18nDynamicSource {
	// jw.project.name.%s, where %s will be replaced with the project slug
	PROJECT_NAME("jw.project.name.%s"),

	// jw.project.description.%s, where %s will be replaced with the project slug
	PROJECT_DESCRIPTION("jw.project.description.%s"),
	;
}
