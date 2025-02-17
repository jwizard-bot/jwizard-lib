package pl.jwizard.jwl.node

import pl.jwizard.jwl.property.BaseEnvironment
import pl.jwizard.jwl.util.logger
import pl.jwizard.jwl.vault.VaultClient

class AudioNodesCache(environment: BaseEnvironment) {
	companion object {
		private val log = logger<AudioNodesCache>()
	}

	private val vaultClient: VaultClient = VaultClient(environment)

	val nodes = mutableSetOf<AudioNodeDefinition>()

	fun fetchAndReloadNodes() {
		vaultClient.init()
		nodes.clear()
		val jsonObject = vaultClient.readKvSecretsAsJson("audio-nodes")
		val audioNodes = jsonObject.get("audioNodes").asArray()
		for (node in audioNodes) {
			nodes.add(AudioNodeDefinition(node.asObject()))
		}
		log.info("Reload: {} audio nodes in cache.", nodes.size)
	}
}
