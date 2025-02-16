package pl.jwizard.jwl

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.util.StdDateFormat
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import pl.jwizard.jwl.ioc.stereotype.SingletonComponent
import pl.jwizard.jwl.ioc.stereotype.SingletonObject

@SingletonComponent
class JacksonInitializerBean {
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
