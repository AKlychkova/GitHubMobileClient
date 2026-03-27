package tech.kts.metaclass.githubmobileclient.platform

import tech.kts.metaclass.githubmobileclient.authLauncher

actual fun getAuthLauncher(): AuthLauncher = authLauncher!!