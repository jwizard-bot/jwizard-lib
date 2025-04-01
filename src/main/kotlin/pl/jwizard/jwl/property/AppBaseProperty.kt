package pl.jwizard.jwl.property

import kotlin.reflect.KClass

enum class AppBaseProperty(
	override val key: String,
	override val type: KClass<*> = String::class,
) : AppProperty {
	// @formatter:off

	// determinate, if application at start should load environment variables from .env file
	ENV_ENABLED("env.enabled", Boolean::class),

	// server port used for embed Jetty server
	SERVER_PORT("server.port", Int::class),

	// used for create links refer to online repository
	VCS_ORGANIZATION_NAME("vcs.organization-name"),

	// database connection
	DB_URL("db.jdbc"),
	DB_USERNAME("db.username"),
	DB_PASSWORD("db.password"),
	DB_DRIVER_CLASS_NAME("db.driver-class-name"),
	DB_POOL_MAX_SIZE("db.pool.max-size", Int::class),
	DB_POOL_MIN_IDLE("db.pool.min-idle", Int::class),
	DB_POOL_TIMEOUT_IDLE("db.pool.timeout.idle-millis", Long::class),
	DB_POOL_TIMEOUT_CONNECTION("db.pool.timeout.connection-millis", Long::class),

	// vault connection
	VAULT_URL("vault.url"),
	VAULT_AUTHENTICATION_TYPE("vault.authentication.type"),
	VAULT_TOKEN("vault.token"),
	VAULT_USERNAME("vault.username"),
	VAULT_PASSWORD("vault.password"),
	VAULT_KV_BACKEND("vault.kv.backend"),
	VAULT_KV_DEFAULT_CONTEXT("vault.kv.default-context"),

	// proxy
	PROXY_VERIFICATION_HEADER_NAME("proxy.verification.header-name"),
	PROXY_VERIFICATION_TOKEN("proxy.verification.token"),

	// guild default properties
	GUILD_VOTING_PERCENTAGE_RATIO("guild.default.voting-percentage-ratio", Int::class),
	GUILD_MAX_VOTING_TIME_SEC("guild.default.time-to-finish-voting-sec", Long::class),
	GUILD_DJ_ROLE_NAME("guild.default.dj-role-name"),
	GUILD_MIN_REPEATS_OF_TRACK("guild.default.min-repeats-of-track", Int::class),
	GUILD_MAX_REPEATS_OF_TRACK("guild.default.max-repeats-of-track", Int::class),
	GUILD_LEAVE_EMPTY_CHANNEL_SEC("guild.default.leave-empty-channel-sec", Long::class),
	GUILD_LEAVE_NO_TRACKS_SEC("guild.default.leave-no-tracks-channel-sec", Long::class),
	GUILD_PLAYER_VOLUME("guild.default.player-volume", Int::class),
	GUILD_RANDOM_AUTO_CHOOSE_TRACK("guild.default.random-auto-choose-track", Boolean::class),
	GUILD_TIME_AFTER_AUTO_CHOOSE_SEC("guild.default.time-after-auto-choose-sec", Long::class),
	GUILD_MAX_TRACKS_TO_CHOOSE("guild.default.tracks-to-choose-max", Int::class),
	GUILD_LEGACY_PREFIX("guild.default.legacy-prefix"),
	GUILD_SLASH_ENABLED("guild.default.slash-enabled", Boolean::class),
	GUILD_SUPPRESS_RESPONSE_NOTIFICATIONS("guild.default.suppress-response-notifications", Boolean::class),

	PLAYER_MAX_VOLUME("player.default.max-volume", Int::class),

	// default i18n language (when no one is selected)
	I18N_DEFAULT_LANGUAGE("i18n.default-language"),
	;

	// @formatter:on
}
