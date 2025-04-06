package pl.jwizard.jwl.server.useragent

import nl.basjes.parse.useragent.UserAgent
import nl.basjes.parse.useragent.UserAgentAnalyzer
import pl.jwizard.jwl.property.AppBaseProperty
import pl.jwizard.jwl.property.BaseEnvironment

// very costly class!, declare only ONE (as singleton bean) for all application
class UserAgentExtractor(environment: BaseEnvironment) {
	private val userAgentAnalyzer = UserAgentAnalyzer.newBuilder()
		.withCache(environment.getProperty(AppBaseProperty.YAUAA_CACHE_MAX_ELEMENTS))
		.withFields(UserAgent.OPERATING_SYSTEM_NAME, UserAgent.DEVICE_CLASS)
		.build()

	fun analyzeAndExtract(userAgent: String?): UserAgentData {
		val analyzerResult = userAgentAnalyzer.parse(userAgent)
		val deviceType = analyzerResult.getValue(UserAgent.DEVICE_CLASS)
		return UserAgentData(
			deviceSystem = analyzerResult.getValue(UserAgent.OPERATING_SYSTEM_NAME),
			deviceMobile = when (deviceType?.lowercase()) {
				"phone" -> true
				"desktop" -> false
				else -> null
			}
		)
	}
}
