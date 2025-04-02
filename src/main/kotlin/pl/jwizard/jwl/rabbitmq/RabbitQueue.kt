package pl.jwizard.jwl.rabbitmq

enum class RabbitQueue(val queueName: String) {
	CORE_TO_API_LISTEN_STATS("core_to_api_listen_stats"),
	CORE_TO_API_CMD_STATS("core_to_api_cmd_stats"),
	;
}
