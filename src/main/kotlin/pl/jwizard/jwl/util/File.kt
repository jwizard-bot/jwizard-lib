/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.util

import org.springframework.core.io.ClassPathResource

/**
 * Finds a resource by its path in multiple containers (directories) within the classpath.
 *
 * This function iterates over a list of container paths, attempting to locate the given resource in each of them.
 * The first container that contains the resource will be returned, or `null` if the resource cannot be found in any of
 * the containers.
 *
 * @param containers A list of container paths (directories) where the resource might be located.
 * @param path The relative path to the resource within the containers.
 * @return A [ClassPathResource] representing the resource if found, or `null` if not found.
 * @author Miłosz Gilga
 */
fun findResourceInMultipleContainers(containers: List<String>, path: String) = containers
	.map { ClassPathResource("$it/$path") }
	.firstOrNull { it.exists() }
