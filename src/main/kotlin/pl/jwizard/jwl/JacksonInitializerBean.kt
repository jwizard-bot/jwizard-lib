/*
 * Copyright (c) 2025 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.util.StdDateFormat
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import pl.jwizard.jwl.ioc.stereotype.SingletonComponent
import pl.jwizard.jwl.ioc.stereotype.SingletonObject

/**
 * An IoC component that initializes the Jackson ObjectMapper. This class provides a configured instance of
 * [ObjectMapper] with specific serialization settings.
 *
 * @author Miłosz Gilga
 */
@SingletonComponent
class JacksonInitializerBean {

	/**
	 * Creates and configures an ObjectMapper bean. This method sets up the [ObjectMapper] to avoid writing dates as
	 * timestamps.
	 *
	 * @return A configured instance of [ObjectMapper].
	 */
	@SingletonObject
	fun objectMapper(): ObjectMapper {
		val objectMapper = ObjectMapper()
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
		// mappers
		objectMapper.registerModule(JavaTimeModule())
		// formatters
		objectMapper.dateFormat = StdDateFormat()
		return objectMapper
	}
}
