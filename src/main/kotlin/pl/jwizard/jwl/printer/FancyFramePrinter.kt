package pl.jwizard.jwl.printer

import org.springframework.core.io.ClassPathResource
import java.nio.charset.StandardCharsets

internal class FancyFramePrinter(
	fileClasspathLocation: String,
	printer: Printer,
) : AbstractPrinter(printer) {
	companion object {
		private const val HORIZONTAL_BORDER = "─"
		private const val VERTICAL_BORDER = "│"
		private val TOP_EDGE: (String) -> String = { "╭$it╮" }
		private val BOTTOM_EDGE: (String) -> String = { "╰$it╯" }
	}

	private val classPathResource = ClassPathResource(fileClasspathLocation)

	override fun setBodyContent(): String? {
		val rawFrameElements = classPathResource.inputStream
			.bufferedReader(StandardCharsets.UTF_8)
			.use { it.readText() }

		val frameElements = rawFrameElements
			.split(regex = "\\r?\\n|\\r".toRegex())
			.dropLastWhile { it.isEmpty() }
			.toTypedArray()

		if (frameElements.isEmpty()) {
			return null
		}
		val maxLengthOfSingleFrameElement = frameElements.maxOf { it.length }
		val horizontalBorder = HORIZONTAL_BORDER.repeat(maxLengthOfSingleFrameElement + 2)

		val lines = mutableListOf<String>()
		lines.add(TOP_EDGE(horizontalBorder))
		frameElements.forEach {
			lines.add("$VERTICAL_BORDER ${it.padEnd(maxLengthOfSingleFrameElement)} $VERTICAL_BORDER")
		}
		lines.add(BOTTOM_EDGE(horizontalBorder))

		return lines.joinToString(separator = "\n")
	}
}
