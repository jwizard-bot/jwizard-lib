package pl.jwizard.jwl.persistence.sql

import org.jdbi.v3.core.HandleCallback
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.statement.Query
import pl.jwizard.jwl.ioc.stereotype.SingletonComponent
import kotlin.reflect.KClass
import kotlin.reflect.cast

@SingletonComponent
class JdbiQueryBean(private val jdbi: Jdbi) {
	companion object {
		private const val START_DELIMITER = "{{"
		private const val STOP_DELIMITER = "}}"
	}

	fun parse(input: String, replacements: Map<String, Any>): String {
		var result = input
		for ((key, value) in replacements) {
			result = result.replace("$START_DELIMITER$key$STOP_DELIMITER", value.toString())
		}
		return result
	}

	fun <T : Any> queryForNullableObject(sql: String, type: KClass<T>, vararg args: Any): T? = try {
		jdbi.withHandle<T?, Exception> {
			val statement = it.createQuery(sql.trimIndent())
			args.forEachIndexed { index, arg -> statement.bind(index, arg) }
			statement.mapTo(type.java).one()
		}
	} catch (ex: Exception) {
		null
	}

	fun queryForBool(
		sql: String,
		vararg args: Any,
	) = queryForNullableObject(sql.trimIndent(), Boolean::class, *args) ?: false

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

	fun <T : Any> queryForList(
		sql: String,
		type: KClass<T>,
		vararg args: Any,
	): List<T> = withBaseHandler(sql, *args) {
		it.mapTo(type.java).list()
	}

	fun <T : Any> queryForObject(
		sql: String,
		type: KClass<T>,
		vararg args: Any,
	): T = withBaseHandler(sql, *args) {
		it.mapTo(type.java).single()
	}

	fun queryForSingleMap(
		sql: String,
		vararg args: Any,
	): Map<String, Any?> = withBaseHandler(sql, *args) {
		it.mapToMap().single()
	}

	fun <T> inTransaction(callback: HandleCallback<T, Exception>): T = jdbi.inTransaction(callback)

	fun update(sql: String, vararg args: Any) = jdbi.withHandle<Int, Exception> {
		val statement = it.createUpdate(sql.trimIndent())
		args.forEachIndexed { index, arg -> statement.bind(index, arg) }
		statement.execute()
	}

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

	private fun <T> withBaseHandler(
		sql: String,
		vararg args: Any,
		onQuery: (Query) -> T,
	) = jdbi.withHandle<T, Exception> {
		val statement = it.createQuery(sql.trimIndent())
		args.forEachIndexed { index, arg -> statement.bind(index, arg) }
		onQuery(statement)
	}
}
