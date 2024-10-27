/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.property

import pl.jwizard.jwl.property.AppBaseProperty.*
import kotlin.reflect.KClass

/**
 * Enum class representing configuration properties.
 *
 * Defining following properties:
 * - [ENV_ENABLED]: Determines if the application should load environment variables from a .env file at startup.
 * - [DEPLOYMENT_BUILD_VERSION]: Deployment build version. Generated by CI/CD pipeline. Default value: *UNKNOWN*.
 * - [DEPLOYMENT_LAST_BUILD_DATE]: Deployment last build date. Generated by CI/CD pipeline. Default value: *UNKNOWN*.
 * - [DB_URL]: Database JDBC URL provider.
 * - [DB_USERNAME]: Database username.
 * - [DB_PASSWORD]: Database password.
 * - [DB_DRIVER_CLASS_NAME]: Database driver class name (full qualified path).
 * - [VAULT_URL]: Vault key storage URL.
 * - [VAULT_TOKEN]: Vault key storage access token.
 * - [VAULT_KV_BACKEND]: Vault key storage KV backend name.
 * - [VAULT_KV_DEFAULT_CONTEXT]: Vault key storage KV default context. Load default secrets independently of
 *   application name.
 * - [VAULT_KV_APPLICATION_NAME]: Vault key storage KV application name. Load all secrets from this pre-path.
 * - [S3_PUBLIC_API_URL]: The URL or endpoint for accessing the public S3 API.
 * - [S3_HOST_URL]: The host address of the S3 service.
 * - [S3_REGION]: The region where the S3 bucket is hosted.
 * - [S3_ACCESS_KEY]: The access key for S3 authentication.
 * - [S3_SECRET_KEY]: The secret key for S3 authentication.
 * - [S3_ROOT_BUCKET]: The name of the root bucket in the S3 storage.
 * - [S3_PATH_STYLE_ACCESS_ENABLED]: Determines if path-style access is enabled for the S3 service.
 * - [GUILD_VOTING_PERCENTAGE_RATIO]: Ratio of voting percentage for guilds.
 * - [GUILD_MAX_VOTING_TIME_SEC]: Maximum voting time for guilds in seconds.
 * - [GUILD_DJ_ROLE_NAME]: Name of the DJ role in guilds.
 * - [GUILD_MIN_REPEATS_OF_TRACK]: Minimum number of repeats allowed for a track in guilds.
 * - [GUILD_MAX_REPEATS_OF_TRACK]: Maximum number of repeats allowed for a track in guilds.
 * - [GUILD_LEAVE_EMPTY_CHANNEL_SEC]: Time in seconds after which the bot leaves an empty channel in guilds.
 * - [GUILD_LEAVE_NO_TRACKS_SEC]: Time in seconds after which the bot leaves a channel with no tracks in guilds.
 * - [GUILD_DEFAULT_VOLUME]: Default volume level for guilds.
 * - [GUILD_RANDOM_AUTO_CHOOSE_TRACK]: Indicates whether to randomly auto-choose tracks in guilds.
 * - [GUILD_TIME_AFTER_AUTO_CHOOSE_SEC]: Time in seconds after which the bot automatically chooses a track in guilds.
 * - [GUILD_MAX_TRACKS_TO_CHOOSE]: Maximum number of tracks to choose from in guilds.
 * - [GUILD_DEFAULT_LEGACY_PREFIX]: Default legacy command prefix used in guild.
 * - [GUILD_DEFAULT_SLASH_ENABLED]: Determines if the slash command system is enabled by default in guild.
 * - [PLAYER_MAX_VOLUME]: Max discord player volume (in units).
 * - [I18N_DEFAULT_LANGUAGE]: I18n default language (as language tag, without localization property).
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
	 * Deployment build version. Generated by CI/CD pipeline. Default value: *UNKNOWN*.
	 */
	DEPLOYMENT_BUILD_VERSION("deployment.build-version"),

	/**
	 * Deployment last build date. Generated by CI/CD pipeline. Default value: *UNKNOWN*.
	 */
	DEPLOYMENT_LAST_BUILD_DATE("deployment.last-build-date"),

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
	 * Vault key storage url.
	 */
	VAULT_URL("vault.url"),

	/**
	 * Vault key storage access token.
	 */
	VAULT_TOKEN("vault.token"),

	/**
	 * Vault key storage KV backend name.
	 */
	VAULT_KV_BACKEND("vault.kv.backend"),

	/**
	 * Vault key storage KV default context. Load default secrets independently of application name.
	 */
	VAULT_KV_DEFAULT_CONTEXT("vault.kv.default-context"),

	/**
	 * Vault key storage KV application name. Load all secrets from this pre-path.
	 */
	VAULT_KV_APPLICATION_NAME("vault.kv.application-name"),

	/**
	 * The URL or endpoint for accessing the public S3 API.
	 */
	S3_PUBLIC_API_URL("s3.public-api-url"),

	/**
	 * The host address of the S3 service.
	 */
	S3_HOST_URL("s3.host-url"),

	/**
	 * The region where the S3 bucket is hosted.
	 */
	S3_REGION("s3.region"),

	/**
	 * The access key for S3 authentication.
	 */
	S3_ACCESS_KEY("s3.access-key"),

	/**
	 * The secret key for S3 authentication.
	 */
	S3_SECRET_KEY("s3.secret-key"),

	/**
	 * The name of the root bucket in the S3 storage.
	 */
	S3_ROOT_BUCKET("s3.root-bucket"),

	/**
	 * Determines if path-style access is enabled for the S3 service.
	 */
	S3_PATH_STYLE_ACCESS_ENABLED("s3.path-style-access-enabled", Boolean::class),

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
	 * Default volume level for guilds.
	 */
	GUILD_DEFAULT_VOLUME("guild.default.default-volume", Int::class),

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
	GUILD_DEFAULT_LEGACY_PREFIX("guild.default.default-legacy-prefix"),

	/**
	 * Determines if the slash command system is enabled by default in guild.
	 */
	GUILD_DEFAULT_SLASH_ENABLED("guild.default.default-slash-enabled", Boolean::class),

	/**
	 * Max discord player volume (in units).
	 */
	PLAYER_MAX_VOLUME("player.default.max-volume", Int::class),

	/**
	 * I18n default language (as language tag, without localization property).
	 */
	I18N_DEFAULT_LANGUAGE("i18n.default-language"),
	;
}