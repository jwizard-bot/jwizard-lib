/*
 * Copyright (c) 2025 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.property

import pl.jwizard.jwl.property.AppBaseProperty.*
import pl.jwizard.jwl.property.vault.VaultAuthenticationType
import kotlin.reflect.KClass

/**
 * Enum class representing configuration properties.
 *
 * Defining following properties:
 * - [ENV_ENABLED]: Determines if the application should load environment variables from a .env file at startup.
 * - [SERVER_PORT]: Server port used for standalone Jetty server.
 * - [VCS_ALL]: Property defining all version control system (VCS) repositories.
 * - [VCS_ORGANIZATION_NAME]: Version Control System organization name. Used for create links refer to online repository.
 * - [VCS_REPOSITORY_JW_CORE]: Name of the `jw-core` version control system repository.
 * - [VCS_REPOSITORY_JW_API]: Name of the `jw-api` version control system repository.
 * - [VCS_REPOSITORY_JW_LIB]: Name of the `jw-lib` version control system repository.
 * - [VCS_REPOSITORY_JW_WEB]: Name of the `jw-web` version control system repository.
 * - [VCS_REPOSITORY_JW_TOOLS]: Name of the `jw-tools` version control system repository.
 * - [VCS_REPOSITORY_JW_AUDIO_CLIENT]: Name of the `jw-audio-client` version control system repository.
 * - [DB_URL]: Database JDBC URL provider.
 * - [DB_USERNAME]: Database username.
 * - [DB_PASSWORD]: Database password.
 * - [DB_DRIVER_CLASS_NAME]: Database driver class name (full qualified path).
 * - [DB_POOL_MAX_SIZE]: The maximum number of connections allowed in the database connection pool.
 * - [DB_POOL_MIN_IDLE]: The minimum number of idle connections the pool should maintain.
 * - [DB_POOL_TIMEOUT_IDLE]: The timeout (in milliseconds) after which idle connections will be closed.
 * - [DB_POOL_TIMEOUT_CONNECTION]: The timeout (in milliseconds) for establishing a new database connection.
 * - [VAULT_URL]: Vault key storage URL.
 * - [VAULT_AUTHENTICATION_TYPE]: Vault key storage authentication type.
 * - [VAULT_TOKEN]: Vault key storage access token.
 * - [VAULT_USERNAME]: Vault key storage username.
 * - [VAULT_PASSWORD]: Vault key storage password.
 * - [VAULT_KV_VERSION]: Vault key storage KV version.
 * - [VAULT_KV_BACKEND]: Vault key storage KV backend name.
 * - [VAULT_KV_DEFAULT_CONTEXT]: Vault key storage KV default context. Load default secrets independently of
 *   application name.
 * - [GUILD_VOTING_PERCENTAGE_RATIO]: Ratio of voting percentage for guilds.
 * - [GUILD_MAX_VOTING_TIME_SEC]: Maximum voting time for guilds in seconds.
 * - [GUILD_DJ_ROLE_NAME]: Name of the DJ role in guilds.
 * - [GUILD_MIN_REPEATS_OF_TRACK]: Minimum number of repeats allowed for a track in guilds.
 * - [GUILD_MAX_REPEATS_OF_TRACK]: Maximum number of repeats allowed for a track in guilds.
 * - [GUILD_LEAVE_EMPTY_CHANNEL_SEC]: Time in seconds after which the bot leaves an empty channel in guilds.
 * - [GUILD_LEAVE_NO_TRACKS_SEC]: Time in seconds after which the bot leaves a channel with no tracks in guilds.
 * - [GUILD_PLAYER_VOLUME]: Default volume level for guilds.
 * - [GUILD_RANDOM_AUTO_CHOOSE_TRACK]: Indicates whether to randomly auto-choose tracks in guilds.
 * - [GUILD_TIME_AFTER_AUTO_CHOOSE_SEC]: Time in seconds after which the bot automatically chooses a track in guilds.
 * - [GUILD_MAX_TRACKS_TO_CHOOSE]: Maximum number of tracks to choose from in guilds.
 * - [GUILD_LEGACY_PREFIX]: Default legacy command prefix used in guild.
 * - [GUILD_SLASH_ENABLED]: Determines if the slash command system is enabled by default in guild.
 * - [GUILD_SUPPRESS_RESPONSE_NOTIFICATIONS]: Determines if notifications from bot responses should be suppressed.
 * - [PLAYER_MAX_VOLUME]: Max discord player volume (in units).
 * - [I18N_DEFAULT_LANGUAGE]: I18n default language (as language tag, without localization property).
 * - [SERVICE_DOCS_URL]: The base URL for service documentation links.
 *
 * @property key The key used to retrieve the property value from various property sources.
 * @property type The type of the property value. Defaults to [String] if not specified.
 * @author Miłosz Gilga
 */
enum class AppBaseProperty(
	override val key: String,
	override val type: KClass<*> = String::class,
) : AppProperty {

	/**
	 * Determinate, if application at start should load environment variables from .env file.
	 */
	ENV_ENABLED("env.enabled", Boolean::class),

	/**
	 * Server port used for standalone Jetty server.
	 */
	SERVER_PORT("server.port", Int::class),

	/**
	 * Property defining all version control system (VCS) repositories.
	 */
	VCS_ALL("vcs.all"),

	/**
	 * Version Control System organization name. Used for create links refer to online repository.
	 */
	VCS_ORGANIZATION_NAME("vcs.organization-name"),

	/**
	 * Name of the `jw-core` version control system repository.
	 */
	VCS_REPOSITORY_JW_CORE("vcs.repository.jw-core"),

	/**
	 * Name of the `jw-api` version control system repository.
	 */
	VCS_REPOSITORY_JW_API("vcs.repository.jw-api"),

	/**
	 * Name of the `jw-lib` version control system repository.
	 */
	VCS_REPOSITORY_JW_LIB("vcs.repository.jw-lib"),

	/**
	 * Name of the `jw-web` version control system repository.
	 */
	VCS_REPOSITORY_JW_WEB("vcs.repository.jw-web"),

	/**
	 * Name of the `jw-tools` version control system repository.
	 */
	VCS_REPOSITORY_JW_TOOLS("vcs.repository.jw-tools"),

	/**
	 * Name of the `jw-audio-client` version control system repository.
	 */
	VCS_REPOSITORY_JW_AUDIO_CLIENT("vcs.repository.jw-audio-client"),

	/**
	 * Database JDBC url provider.
	 */
	DB_URL("db.jdbc"),

	/**
	 * Database username.
	 */
	DB_USERNAME("db.username"),

	/**
	 * Database password.
	 */
	DB_PASSWORD("db.password"),

	/**
	 * Database driver class name (full qualified path).
	 */
	DB_DRIVER_CLASS_NAME("db.driver-class-name"),

	/**
	 * The maximum number of connections allowed in the database connection pool.
	 */
	DB_POOL_MAX_SIZE("db.pool.max-size", Int::class),

	/**
	 * The minimum number of idle connections the pool should maintain.
	 */
	DB_POOL_MIN_IDLE("db.pool.min-idle", Int::class),

	/**
	 * The timeout (in milliseconds) after which idle connections will be closed.
	 */
	DB_POOL_TIMEOUT_IDLE("db.pool.timeout.idle-millis", Long::class),

	/**
	 * The timeout (in milliseconds) for establishing a new database connection.
	 */
	DB_POOL_TIMEOUT_CONNECTION("db.pool.timeout.connection-millis", Long::class),

	/**
	 * Vault key storage url.
	 */
	VAULT_URL("vault.url"),

	/**
	 * Vault key storage authentication type.
	 * @see VaultAuthenticationType
	 */
	VAULT_AUTHENTICATION_TYPE("vault.authentication.type"),

	/**
	 * Vault key storage access token.
	 */
	VAULT_TOKEN("vault.token"),

	/**
	 * Vault key storage username.
	 */
	VAULT_USERNAME("vault.username"),

	/**
	 * Vault key storage password.
	 */
	VAULT_PASSWORD("vault.password"),

	/**
	 * Vault key storage KV backend name.
	 */
	VAULT_KV_BACKEND("vault.kv.backend"),

	/**
	 * Vault key storage KV backend version.
	 */
	VAULT_KV_VERSION("vault.kv.version", Int::class),

	/**
	 * Vault key storage KV default context. Load default secrets independently of application name.
	 */
	VAULT_KV_DEFAULT_CONTEXT("vault.kv.default-context"),

	/**
	 * Ratio of voting percentage for guilds.
	 */
	GUILD_VOTING_PERCENTAGE_RATIO("guild.default.voting-percentage-ratio", Int::class),

	/**
	 * Maximum voting time for guilds in seconds.
	 */
	GUILD_MAX_VOTING_TIME_SEC("guild.default.time-to-finish-voting-sec", Long::class),

	/**
	 * Name of the DJ role in guilds.
	 */
	GUILD_DJ_ROLE_NAME("guild.default.dj-role-name"),

	/**
	 * Minimum number of tracks to choose from in guilds.
	 */
	GUILD_MIN_REPEATS_OF_TRACK("guild.default.min-repeats-of-track", Int::class),

	/**
	 * Maximum number of repeats allowed for a track in guilds.
	 */
	GUILD_MAX_REPEATS_OF_TRACK("guild.default.max-repeats-of-track", Int::class),

	/**
	 * Time in seconds after which the bot leaves an empty channel in guilds.
	 */
	GUILD_LEAVE_EMPTY_CHANNEL_SEC("guild.default.leave-empty-channel-sec", Long::class),

	/**
	 * Time in seconds after which the bot leaves a channel with no tracks in guilds.
	 */
	GUILD_LEAVE_NO_TRACKS_SEC("guild.default.leave-no-tracks-channel-sec", Long::class),

	/**
	 * Default player volume level for guilds.
	 */
	GUILD_PLAYER_VOLUME("guild.default.player-volume", Int::class),

	/**
	 * Indicates whether to randomly auto-choose tracks in guilds.
	 */
	GUILD_RANDOM_AUTO_CHOOSE_TRACK("guild.default.random-auto-choose-track", Boolean::class),

	/**
	 * Time in seconds after which the bot automatically chooses a track in guilds.
	 */
	GUILD_TIME_AFTER_AUTO_CHOOSE_SEC("guild.default.time-after-auto-choose-sec", Long::class),

	/**
	 * Maximum number of tracks to choose from in guilds.
	 */
	GUILD_MAX_TRACKS_TO_CHOOSE("guild.default.tracks-to-choose-max", Int::class),

	/**
	 * Default legacy command prefix used in guild.
	 */
	GUILD_LEGACY_PREFIX("guild.default.legacy-prefix"),

	/**
	 * Determines if the slash command system is enabled by default in guild.
	 */
	GUILD_SLASH_ENABLED("guild.default.slash-enabled", Boolean::class),

	/**
	 * Determines if notifications from bot responses should be suppressed.
	 */
	GUILD_SUPPRESS_RESPONSE_NOTIFICATIONS("guild.default.suppress-response-notifications", Boolean::class),

	/**
	 * Max discord player volume (in units).
	 */
	PLAYER_MAX_VOLUME("player.default.max-volume", Int::class),

	/**
	 * I18n default language (as language tag, without localization property).
	 */
	I18N_DEFAULT_LANGUAGE("i18n.default-language"),

	/**
	 * The base URL for service documentation links.
	 */
	SERVICE_DOCS_URL("service.docs-url"),
	;
}
