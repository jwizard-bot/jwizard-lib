package pl.jwizard.jwl.node

import io.github.jopenlibs.vault.json.JsonObject

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
