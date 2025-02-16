package pl.jwizard.jwl.persistence.sql

import kotlin.reflect.KClass

data class ColumnDef<T : Any>(
	val columnName: String,
	val type: KClass<T>,
)
