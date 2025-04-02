package pl.jwizard.jwl.rabbitmq

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.amqp.core.Message
import pl.jwizard.jwl.util.logger
import kotlin.reflect.KClass

abstract class RabbitMqReceiver<T : Any> {
	companion object {
		private val log = logger<RabbitMqReceiver<*>>()
	}

	internal fun onRawMessage(
		rawMessage: Message,
		objectMapper: ObjectMapper,
		clazz: KClass<T>,
	) = try {
		val payload = objectMapper.readValue(rawMessage.body, clazz.java)
		onMessage(rawMessage, payload)
	} catch (ex: Exception) {
		log.error(
			"Exception while processing message for: \"{}\". Cause: \"{}\".",
			queue.queueName,
			ex.message,
		)
	}

	abstract val queue: RabbitQueue

	abstract fun onMessage(message: Message, payload: T)
}
