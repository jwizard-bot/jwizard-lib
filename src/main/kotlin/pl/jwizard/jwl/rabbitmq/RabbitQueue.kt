package pl.jwizard.jwl.rabbitmq

enum class RabbitQueue(val queueName: String) {
	JW_CORE_TO_API_LISTEN_STATS("jw_core_to_api_listen_stats"),
	JW_CORE_TO_API_CMD_STATS("jw_core_to_api_cmd_stats"),
	;
}
