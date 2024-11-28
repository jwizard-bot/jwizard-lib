/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.persistence.sql

import org.jdbi.v3.core.Handle
import org.jdbi.v3.core.HandleCallback
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.statement.Query
import pl.jwizard.jwl.ioc.stereotype.SingletonComponent
import kotlin.reflect.KClass
import kotlin.reflect.cast

/**
 * A custom extension of [Jdbi] that provides additional utility methods for querying and parsing.
 *
 * This class extends [Jdbi] to add custom methods for handling SQL queries and processing results. It includes
 * functionality for safely querying for objects that might not exist, parsing SQL queries with placeholders, and
 * converting values to long integers.
 *
 * @property jdbi The configured [Jdbi] instance.
 * @author Miłosz Gilga
 */
@SingletonComponent
class JdbiQueryBean(private val jdbi: Jdbi) {

	companion object {
		/**
		 * The start delimiter used in query parsing.
		 */
		private const val START_DELIMITER = "{{"

		/**
		 * The stop delimiter used in query parsing.
		 */
		private const val STOP_DELIMITER = "}}"
	}

	/**
	 * Replaces placeholders in the input string with the provided replacement values.
	 *
	 * This method processes the input string, replacing occurrences of placeholders in the format `{{key}}` with
	 * corresponding values from the [replacements] map.
	 *
	 * @param input The string containing placeholders to replace.
	 * @param replacements A map of placeholders and their replacement values.
	 * @return The resulting string with placeholders replaced by actual values, or an empty string if input is null.
	 */
	fun parse(input: String, replacements: Map<String, Any>): String {
		var result = input
		for ((key, value) in replacements) {
			result = result.replace("$START_DELIMITER$key$STOP_DELIMITER", value.toString())
		}
		return result
	}

	/**
	 * Executes a query and retrieves a single result as a data class.
	 *
	 * This method executes the provided SQL query and attempts to map the result to the specified data class type [T].
	 * It returns the first result or null if no result is found.
	 *
	 * @param T The type of the data class to which the result should be mapped.
	 * @param sql The SQL query to execute. Positional placeholders (`?`) can be used for parameters.
	 * @param type The [KClass] representing the data class type for the result.
	 * @param args Optional arguments for the SQL query.
	 * @return The result of the query mapped to type [T], or null if no result is found.
	 */
	fun <T : Any> queryForNullableObject(sql: String, type: KClass<T>, vararg args: Any): T? = try {
		jdbi.withHandle<T?, Exception> {
			val statement = it.createQuery(sql.trimIndent())
			args.forEachIndexed { index, arg -> statement.bind(index, arg) }
			statement.mapTo(type.java).one()
		}
	} catch (ex: Exception) {
		null
	}

	/**
	 * Executes a query and retrieves a single boolean result, defaulting to false if no result is found.
	 *
	 * This method executes the provided SQL query and attempts to map the result to a boolean value. If no result is
	 * found, it defaults to returning false.
	 *
	 * @param sql The SQL query to execute. Positional placeholders (`?`) can be used for parameters.
	 * @param args Optional arguments for the SQL query.
	 * @return The result of the query as a boolean value, or false if no result is found.
	 */
	fun queryForBool(sql: String, vararg args: Any) =
		queryForNullableObject(sql.trimIndent(), Boolean::class, *args) ?: false

	/**
	 * Executes a SQL query and returns the results as a map where each entry's key and value are mapped from the
	 * specified columns.
	 *
	 * @param U The type of the key column.
	 * @param V The type of the value column.
	 * @param sql The SQL query to execute. Positional placeholders (`?`) can be used for parameters.
	 * @param key Definition of the key column, including its type and column name.
	 * @param value Definition of the value column, including its type and column name.
	 * @param args Optional arguments for the SQL query.
	 * @return A map where each key-value pair is derived from the specified columns in the query result.
	 */
	fun <U : Any, V : Any> queryForListMap(
		sql: String,
		key: ColumnDef<U>,
		value: ColumnDef<V>,
		vararg args: Any,
	): Map<U, V> = withBaseHandler(sql, *args) {
		it.map { rs, _ ->
			key.type.cast(rs.getObject(key.columnName)) to value.type.cast(rs.getObject(value.columnName))
		}.toMap()
	}

	/**
	 * Executes a query and retrieves a list of results.
	 *
	 * This method executes the provided SQL query and retrieves a list of results mapped to the specified type [T].
	 * If no results are found, it returns an empty list.
	 *
	 * @param T The type to which the results should be mapped.
	 * @param sql The SQL query to execute. Positional placeholders (`?`) can be used for parameters.
	 * @param type The [KClass] representing the target type for the results.
	 * @param args Optional arguments for the SQL query.
	 * @return A list of results cast to type [T], or an empty list if no results are found.
	 */
	fun <T : Any> queryForList(sql: String, type: KClass<T>, vararg args: Any): List<T> = withBaseHandler(sql, *args) {
		it.mapTo(type.java).list()
	}

	/**
	 * Executes a query and maps the result to an object of type [T].
	 *
	 * @param T The type to which the results should be mapped.
	 * @param sql The SQL query to execute. Positional placeholders (`?`) can be used for parameters.
	 * @param type The [KClass] representing the target type for the result.
	 * @param args Optional arguments for the SQL query.
	 * @return The object of type [T] that corresponds to the result of the query.
	 */
	fun <T : Any> queryForObject(sql: String, type: KClass<T>, vararg args: Any): T = withBaseHandler(sql, *args) {
		it.mapTo(type.java).single()
	}

	/**
	 * Executes a query and retrieves a single result as a map.
	 *
	 * This method executes the provided SQL query and maps the result to a map where the column names are the keys and
	 * the corresponding values are the row data. It expects exactly one row in the result set and will throw an
	 * exception if the result set is empty or contains more than one row.
	 *
	 * @param sql The SQL query to execute. Positional placeholders (`?`) can be used for parameters.
	 * @param args A variable number of arguments to bind to the placeholders in the query.
	 * @return A map representing the single row result where keys are column names and values are the corresponding data.
	 * @throws IllegalStateException If the query returns zero or multiple rows.
	 */
	fun queryForSingleMap(sql: String, vararg args: Any): Map<String, Any?> = withBaseHandler(sql, *args) {
		it.mapToMap().single()
	}

	/**
	 * Executes a transactional operation with the provided callback.
	 *
	 * This method begins a new transaction and executes the provided [callback]. If the callback completes successfully,
	 * the transaction is committed; if an exception occurs, the transaction is rolled back.
	 *
	 * @param T The type of the result returned by the callback.
	 * @param callback A function to execute within the transaction. This function receives a [Handle] to perform database
	 *        operations.
	 * @return The result of the callback.
	 * @throws Exception If an error occurs during the execution of the callback or during transaction handling.
	 */
	fun <T> inTransaction(callback: HandleCallback<T, Exception>): T = jdbi.inTransaction(callback)

	/**
	 * Executes an update statement and returns the number of rows affected.
	 *
	 * This method executes the provided SQL update statement (e.g., `INSERT`, `UPDATE`, or `DELETE`) and binds the
	 * specified arguments to the positional placeholders (`?`) in the query. It returns the number of rows that were
	 * modified by the operation.
	 *
	 * @param sql The SQL query to execute, using positional placeholders (`?`) for parameters.
	 * @param args A variable number of arguments to bind to the placeholders in the query.
	 * @return The number of rows affected by the query.
	 */
	fun update(sql: String, vararg args: Any) = jdbi.withHandle<Int, Exception> {
		val statement = it.createUpdate(sql.trimIndent())
		args.forEachIndexed { index, arg -> statement.bind(index, arg) }
		statement.execute()
	}

	/**
	 * Inserts multiple records into the specified table.
	 *
	 * This method constructs an SQL `INSERT` statement based on the provided table name and column definitions,
	 * executes the statement.
	 *
	 * @param tableName The name of the table into which records are inserted.
	 * @param columns A map of column names to [SqlColumn] definitions, where each column contains its value and type.
	 */
	fun insertMultiples(tableName: String, columns: Map<String, SqlColumn>): Int {
		val sql = parse(
			input = "INSERT INTO {{table}}({{columns}}) VALUES ({{questionMarks}})",
			replacements = mapOf(
				"table" to tableName,
				"columns" to columns.keys.joinToString(separator = ","),
				"questionMarks" to List(columns.size) { "?" }.joinToString(separator = ","),
			)
		)
		return jdbi.withHandle<Int, Exception> {
			val statement = it.createUpdate(sql)
			columns.values.forEachIndexed { index, col ->
				statement.bindBySqlType(index, col.value, col.type.vendorTypeNumber)
			}
			statement.execute()
		}
	}

	/**
	 * Executes a query with parameter binding and provides a handler to process the query.
	 *
	 * This method simplifies the execution of SQL queries by handling the creation of the query statement, binding the
	 * provided parameters in positional order, and delegating further query execution or result processing to the
	 * specified handler [onQuery].
	 *
	 * @param T The return type of the query execution or result processing.
	 * @param sql The SQL query to be executed. Positional placeholders (`?`) are expected in the query.
	 * @param args A variable number of arguments to bind to the positional placeholders in the query.
	 * @param onQuery A lambda function that receives the prepared [Query] object and handles its execution
	 *                or result mapping.
	 * @return The result of the query execution or processing as determined by the [onQuery] handler.
	 */
	private fun <T> withBaseHandler(sql: String, vararg args: Any, onQuery: (Query) -> T) =
		jdbi.withHandle<T, Exception> {
			val statement = it.createQuery(sql.trimIndent())
			args.forEachIndexed { index, arg -> statement.bind(index, arg) }
			onQuery(statement)
		}
}
