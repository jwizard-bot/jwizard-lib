package pl.jwizard.jwl.server.useragent

data class UserAgentData(
	val deviceSystem: String,
	// true -> mobile, false -> desktop, null -> unknown device type
	val deviceMobile: Boolean?,
)
