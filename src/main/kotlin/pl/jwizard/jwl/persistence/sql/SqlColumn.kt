package pl.jwizard.jwl.persistence.sql

import java.sql.SQLType

data class SqlColumn(
	val value: Any?,
	val type: SQLType,
)
