package pl.jwizard.jwl.property

import kotlin.reflect.KClass

enum class AppBaseListProperty(
	override val key: String,
	override val separator: String? = null,
	override val listElementsType: KClass<*> = String::class,
) : AppListProperty {
	// dev, prod
	RUNTIME_PROFILES("runtime.profiles", ","),

	// vault application names (kv prefix)
	VAULT_KV_APPLICATION_NAMES("vault.kv.application-names"),

	// bot permissions
	JDA_PERMISSIONS("jda.permissions"),

	// handled languages
	I18N_LANGUAGES("i18n.languages"),
	;
}
