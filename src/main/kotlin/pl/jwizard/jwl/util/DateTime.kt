package pl.jwizard.jwl.util

import java.time.Duration
import java.time.LocalDateTime

fun timeDifference(fromDate: LocalDateTime, toDate: LocalDateTime): Pair<String, Long> {
	val duration = Duration.between(fromDate, toDate)
	return when {
		duration.toMinutes() < 1 -> "now" to 0
		duration.toHours() < 1 -> "m" to duration.toMinutes()
		duration.toDays() < 1 -> "h" to duration.toHours()
		duration.toDays() < 30 -> "d" to duration.toDays()
		else -> "ms" to duration.toDays() / 30
	}
}
