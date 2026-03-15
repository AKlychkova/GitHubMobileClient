package tech.kts.metaclass.githubmobileclient.data.auth

actual fun getAuthLauncher(): AuthLauncher = IosAuthLauncher()