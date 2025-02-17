package pl.jwizard.jwl.property.extractor

internal class CmdPropertyValueExtractor : PropertyValueExtractor(
	CmdPropertyValueExtractor::class,
) {
	override fun setProperties(): Map<Any, Any> = System.getProperties()

	override val extractionKey = "cmd"
}
