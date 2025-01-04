/*
 * Copyright (c) 2025 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.persistence.sql

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.argument.Argument
import org.jdbi.v3.core.argument.ArgumentFactory
import org.jdbi.v3.core.kotlin.KotlinPlugin
import org.jdbi.v3.core.mapper.ColumnMappers
import pl.jwizard.jwl.ioc.stereotype.SingletonComponent
import pl.jwizard.jwl.ioc.stereotype.SingletonObject
import pl.jwizard.jwl.property.AppBaseProperty
import pl.jwizard.jwl.property.BaseEnvironment
import java.math.BigInteger
import java.time.LocalDateTime
import java.util.*

/**
 * Configuration class for initializing the database connection pool and JDBC template.
 *
 * This class provides configuration for the [HikariDataSource], which is used to manage the connection pool for
 * connecting to the database. It also provides a [Jdbi] bean that is used to perform database operations.
 *
 * @property environmentBean The [BaseEnvironment] instance used to retrieve database connection properties.
 * @author Miłosz Gilga
 */
@SingletonComponent
class DbSourceInitializerBean(private val environmentBean: BaseEnvironment) {

	/**
	 * Configures and creates a [HikariDataSource] bean.
	 *
	 * This method sets up the HikariCP connection pool with properties such as JDBC URL, username, password, and
	 * driver class name. The configuration values are retrieved from the [BaseEnvironment] instance.
	 *
	 * @return The configured [HikariDataSource] instance.
	 */
	@SingletonObject
	fun dataSourceBean(): HikariDataSource {
		val config = HikariConfig()
		config.jdbcUrl = environmentBean.getProperty(AppBaseProperty.DB_URL)
		config.username = environmentBean.getProperty(AppBaseProperty.DB_USERNAME)
		config.password = environmentBean.getProperty(AppBaseProperty.DB_PASSWORD)
		config.driverClassName = environmentBean.getProperty(AppBaseProperty.DB_DRIVER_CLASS_NAME)
		config.maximumPoolSize = environmentBean.getProperty(AppBaseProperty.DB_POOL_MAX_SIZE)
		config.minimumIdle = environmentBean.getProperty(AppBaseProperty.DB_POOL_MIN_IDLE)
		config.idleTimeout = environmentBean.getProperty(AppBaseProperty.DB_POOL_TIMEOUT_IDLE)
		config.connectionTimeout = environmentBean.getProperty(AppBaseProperty.DB_POOL_TIMEOUT_CONNECTION)
		return HikariDataSource(config)
	}

	/**
	 * Configures and creates a [Jdbi] bean.
	 *
	 * This method creates a [Jdbi] instance using the configured [HikariDataSource]. The [Jdbi] is used for executing
	 * SQL queries and updates, and provides a simplified way to interact with the database.
	 *
	 * @param dataSourceBean Configured [HikariDataSource] instance.
	 * @return The configured [Jdbi] instance.
	 */
	@SingletonObject
	fun jdbi(dataSourceBean: HikariDataSource): Jdbi {
		val jdbi = Jdbi.create(dataSourceBean)

		jdbi.getConfig(ColumnMappers::class.java).coalesceNullPrimitivesToDefaults = false
		jdbi.installPlugin(KotlinPlugin())

		jdbi.registerArgument(ArgumentFactory { type, value, _ ->
			if (type == BigInteger::class.java) {
				Optional.of(Argument { position, statement, _ -> statement.setString(position, value.toString()) })
			} else {
				Optional.empty()
			}
		})
		jdbi.registerColumnMapper(BigInteger::class.java) { rs, columnNumber, _ ->
			val value = rs.getString(columnNumber)
			value?.let { BigInteger(it) }
		}
		jdbi.registerColumnMapper(LocalDateTime::class.java) { rs, columnNumber, _ ->
			val value = rs.getTimestamp(columnNumber)
			value?.toLocalDateTime()
		}
		return jdbi
	}
}
