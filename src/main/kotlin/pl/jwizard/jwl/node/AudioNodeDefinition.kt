/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.node

import io.github.jopenlibs.vault.json.JsonObject

/**
 * Represents the definition of an audio node, including its connection details and configuration.
 *
 * @property name The unique name of the audio node.
 * @property regionGroup The region group that the node is assigned to.
 * @property password The password used to authenticate with the node.
 * @property host The hostname or IP address of the node.
 * @property port The port number to connect to the node.
 * @property secure Whether the connection to the node uses a secure (SSL/TLS) protocol.
 * @property nodePool The pool to which this node belongs for load balancing.
 * @property active Indicates whether the node is currently active and available for use.
 * @author Miłosz Gilga
 */
data class AudioNodeDefinition(
	val name: String,
	val regionGroup: String,
	val password: String,
	val host: String,
	val port: Int,
	val secure: Boolean,
	val nodePool: String,
	val active: Boolean,
) {

	/**
	 * Constructs an [AudioNodeDefinition] instance from a [JsonObject].
	 *
	 * @param jsonObject The JSON object containing the node's definition details.
	 */
	constructor(jsonObject: JsonObject) : this(
		name = jsonObject.getString("name"),
		regionGroup = jsonObject.getString("regionGroup"),
		password = jsonObject.getString("password"),
		host = jsonObject.getString("host"),
		port = jsonObject.getInt("port"),
		secure = jsonObject.getBoolean("secure"),
		nodePool = jsonObject.getString("nodePool"),
		active = jsonObject.getBoolean("active"),
	)
}
