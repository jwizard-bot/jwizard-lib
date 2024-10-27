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
	 * I18n default language (as language tag, without localization property).
	 */
	I18N_DEFAULT_LANGUAGE("i18n.default-language"),
	;
}
