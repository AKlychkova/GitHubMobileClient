package tech.kts.metaclass.githubmobileclient.platform

actual fun getAuthLauncher(): AuthLauncher = IosAuthLauncher()