package pl.jwizard.jwl.persistence.sql

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.KotlinPlugin
import org.jdbi.v3.core.mapper.ColumnMappers
import pl.jwizard.jwl.property.AppBaseProperty
import pl.jwizard.jwl.property.BaseEnvironment
import pl.jwizard.jwl.util.ext.registerArgument
import java.math.BigInteger
import java.time.LocalDateTime

class DbSourceInitializer {
	fun createJdbi(environment: BaseEnvironment): Jdbi {
		val config = HikariConfig()
		config.jdbcUrl = environment.getProperty(AppBaseProperty.DB_URL)
		config.username = environment.getProperty(AppBaseProperty.DB_USERNAME)
		config.password = environment.getProperty(AppBaseProperty.DB_PASSWORD)
		config.driverClassName = environment.getProperty(AppBaseProperty.DB_DRIVER_CLASS_NAME)
		config.maximumPoolSize = environment.getProperty(AppBaseProperty.DB_POOL_MAX_SIZE)
		config.minimumIdle = environment.getProperty(AppBaseProperty.DB_POOL_MIN_IDLE)
		config.idleTimeout = environment.getProperty(AppBaseProperty.DB_POOL_TIMEOUT_IDLE)
		config.connectionTimeout = environment.getProperty(AppBaseProperty.DB_POOL_TIMEOUT_CONNECTION)

		val dataSource = HikariDataSource(config)
		val jdbi = Jdbi.create(dataSource)

		jdbi.getConfig(ColumnMappers::class.java).coalesceNullPrimitivesToDefaults = false
		jdbi.installPlugin(KotlinPlugin())

		jdbi.registerArgument(BigInteger::class) { statement, position, value ->
			statement.setString(position, value.toString())
		}
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
