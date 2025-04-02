package pl.jwizard.jwl.rabbitmq

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.amqp.AmqpException
import org.springframework.amqp.core.AcknowledgeMode
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.amqp.support.converter.SimpleMessageConverter
import org.springframework.beans.factory.DisposableBean
import pl.jwizard.jwl.property.AppBaseProperty
import pl.jwizard.jwl.property.BaseEnvironment
import pl.jwizard.jwl.util.logger
import kotlin.reflect.KClass

class RabbitMqServer(
	private val objectMapper: ObjectMapper,
	environment: BaseEnvironment,
) : DisposableBean {
	companion object {
		private val log = logger<RabbitMqServer>()
	}

	private val host = environment.getProperty<String>(AppBaseProperty.RABBITMQ_HOST)
	private val port = environment.getProperty<Int>(AppBaseProperty.RABBITMQ_PORT)
	private val username = environment.getProperty<String>(AppBaseProperty.RABBITMQ_USERNAME)
	private val password = environment.getProperty<String>(AppBaseProperty.RABBITMQ_PASSWORD)

	private val serverConnectionFactory = CachingConnectionFactory(host)
	private val rabbitTemplate: RabbitTemplate
	private val receivers = mutableListOf<SimpleMessageListenerContainer>()
	private val definedMessageConverter: MessageConverter

	init {
		serverConnectionFactory.port = port
		serverConnectionFactory.username = username
		serverConnectionFactory.setPassword(password)

		log.info("Connected with RabbitMQ server: {}:{}.", host, port)

		definedMessageConverter = SimpleMessageConverter()
		rabbitTemplate = RabbitTemplate(serverConnectionFactory).apply {
			// by default convert object to bytes
			messageConverter = definedMessageConverter
		}
	}

	fun addQueue(queue: RabbitQueue) {
		rabbitTemplate.execute {
			it.queueDeclare(queue.queueName, true, false, false, null)
		}
	}

	fun <T : Any> addListener(clazz: KClass<T>, receiver: RabbitMqReceiver<T>) {
		val qName = receiver.queue.queueName
		val container = SimpleMessageListenerContainer().apply {
			connectionFactory = serverConnectionFactory
			acknowledgeMode = AcknowledgeMode.MANUAL
			addQueueNames(qName)
			setMessageListener { receiver.onRawMessage(it, objectMapper, clazz) }
		}
		receivers += container
		container.start()
		log.info("Append RabbitMQ queue listener for queue: \"{}\".", qName)
	}

	fun <T : Any> sendToQueue(queue: RabbitQueue, payload: T) {
		val qName = queue.queueName
		try {
			val rawMessage = objectMapper.writeValueAsBytes(payload)
			rabbitTemplate.convertAndSend(qName, rawMessage)
		} catch (ex: AmqpException) {
			log.error("Unable to send message to queue: \"{}\". Cause: \"{}\".", qName, ex.message)
		}
	}

	override fun destroy() {
		receivers.forEach(SimpleMessageListenerContainer::shutdown)
		log.info("Shutting down: {} receivers.", receivers.size)
		receivers.clear()

		serverConnectionFactory.clearConnectionListeners()
		serverConnectionFactory.destroy()
		log.info("Closed RabbitMQ server connection.")
	}
}
