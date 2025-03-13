package pl.jwizard.jwl.util.ext

import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.argument.Argument
import org.jdbi.v3.core.argument.ArgumentFactory
import java.sql.PreparedStatement
import java.util.*
import kotlin.reflect.KClass

fun Jdbi.registerArgument(
	clazz: KClass<*>,
	modifierCallback: (PreparedStatement, Int, Any) -> Unit
): Jdbi = registerArgument(ArgumentFactory { type, value, _ ->
	if (type == clazz.java) {
		Optional.of(Argument { position, statement, _ ->
			modifierCallback(statement, position, value)
		})
	} else {
		Optional.empty()
	}
})
