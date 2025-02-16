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

@SingletonComponent
class DbSourceInitializerBean(private val environmentBean: BaseEnvironment) {
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
		config.connectionTimeout = environmentBean
			.getProperty(AppBaseProperty.DB_POOL_TIMEOUT_CONNECTION)
		return HikariDataSource(config)
	}

	@SingletonObject
	fun jdbi(dataSourceBean: HikariDataSource): Jdbi {
		val jdbi = Jdbi.create(dataSourceBean)

		jdbi.getConfig(ColumnMappers::class.java).coalesceNullPrimitivesToDefaults = false
		jdbi.installPlugin(KotlinPlugin())

		jdbi.registerArgument(ArgumentFactory { type, value, _ ->
			if (type == BigInteger::class.java) {
				Optional.of(Argument { position, statement, _ ->
					statement.setString(
						position,
						value.toString()
					)
				})
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
