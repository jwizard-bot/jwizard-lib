package pl.jwizard.jwl.util

import org.springframework.core.io.ClassPathResource

fun findResourceInMultipleContainers(
	containers: List<String>,
	path: String,
) = containers.map { ClassPathResource("$it/$path") }.firstOrNull { it.exists() }
