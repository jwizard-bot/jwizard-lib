package pl.jwizard.jwl.util.ext

import com.fasterxml.jackson.databind.JsonNode

fun JsonNode.getAsText(key: String) = this.get(key).asText()

fun JsonNode.getAsNullableText(key: String): String? = this.get(key).asText()

fun JsonNode.getAsLong(key: String) = this.get(key).asLong()

fun JsonNode.getAsInt(key: String) = this.get(key).asInt()
