/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.node

import pl.jwizard.jwl.property.BaseEnvironment
import pl.jwizard.jwl.property.vault.VaultClient
import pl.jwizard.jwl.util.logger

/**
 * Handles caching and reloading of audio node definitions. This class interacts with a Vault client to fetch the
 * latest configuration of audio nodes, storing them for quick access.
 *
 * @property environment The application environment configuration used to initialize the Vault client.
 * @author Miłosz Gilga
 */
class AudioNodesCache(private val environment: BaseEnvironment) {

	companion object {
		private val log = logger<AudioNodesCache>()
	}

	/**
	 * The client used to interact with the Vault storage system.
	 */
	private val vaultClient: VaultClient = VaultClient(environment)

	/**
	 * A set containing all currently cached audio node definitions.
	 */
	val nodes = mutableSetOf<AudioNodeDefinition>()

	/**
	 * Fetches and reloads audio node definitions from the Vault storage. Clears the current cache, retrieves the latest
	 * data, and repopulates the [nodes] set.
	 *
	 * @throws IllegalStateException If the Vault client fails to initialize or read secrets.
	 */
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
