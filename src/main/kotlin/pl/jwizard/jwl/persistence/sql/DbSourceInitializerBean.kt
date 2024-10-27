/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.persistence.sql

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.springframework.context.annotation.Bean
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.stereotype.Component
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.TransactionTemplate
import pl.jwizard.jwl.property.AppBaseProperty
import pl.jwizard.jwl.property.BaseEnvironment

/**
 * Configuration class for initializing the database connection pool and JDBC template.
 *
 * This class provides configuration for the [HikariDataSource], which is used to manage the connection pool for
 * connecting to the database. It also provides a [JdbcTemplate] bean that is used to perform database operations.
 *
 * @property environmentBean The [BaseEnvironment] instance used to retrieve database connection properties from
 *           the environment.
 * @author Miłosz Gilga
 */
@Component
class DbSourceInitializerBean(private val environmentBean: BaseEnvironment) {

	/**
	 * Configures and creates a [HikariDataSource] bean.
	 *
	 * This method sets up the HikariCP connection pool with properties such as JDBC URL, username, password, and
	 * driver class name. The configuration values are retrieved from the [BaseEnvironment] instance.
	 *
	 * @return The configured [HikariDataSource] instance.
	 */
	@Bean
	fun dataSourceBean(): HikariDataSource {
		val config = HikariConfig()
		config.jdbcUrl = environmentBean.getProperty(AppBaseProperty.DB_URL)
		config.username = environmentBean.getProperty(AppBaseProperty.DB_USERNAME)
		config.password = environmentBean.getProperty(AppBaseProperty.DB_PASSWORD)
		config.driverClassName = environmentBean.getProperty(AppBaseProperty.DB_DRIVER_CLASS_NAME)
		config.maximumPoolSize = 10
		config.minimumIdle = 5
		config.idleTimeout = 30000
		config.connectionTimeout = 30000
		return HikariDataSource(config)
	}

	/**
	 * Configures and creates a [JdbcKtTemplateBean] bean.
	 *
	 * This method creates a [JdbcKtTemplateBean] instance using the configured [HikariDataSource].
	 * The [JdbcKtTemplateBean] is used for executing SQL queries and updates, and provides a simplified way to
	 * interact with the database.
	 *
	 * @param dataSourceBean Configured [HikariDataSource] instance.
	 * @return The configured [JdbcKtTemplateBean] instance.
	 */
	@Bean
	fun jdbcTemplateBean(dataSourceBean: HikariDataSource) = JdbcKtTemplateBean(dataSourceBean)

	/**
	 * Configures and creates a [DataSourceTransactionManager] bean.
	 *
	 * @param dataSourceBean The configured [HikariDataSource] instance used for transaction management.
	 * @return The configured [DataSourceTransactionManager] instance.
	 */
	@Bean
	fun transactionManager(dataSourceBean: HikariDataSource) = DataSourceTransactionManager(dataSourceBean)

	/**
	 * Configures and creates a [TransactionTemplate] bean.
	 *
	 * @param transactionManager The [PlatformTransactionManager] instance used to manage transactions.
	 * @return The configured [TransactionTemplate] instance.
	 */
	@Bean
	fun transactionTemplate(transactionManager: PlatformTransactionManager) = TransactionTemplate(transactionManager)
}
