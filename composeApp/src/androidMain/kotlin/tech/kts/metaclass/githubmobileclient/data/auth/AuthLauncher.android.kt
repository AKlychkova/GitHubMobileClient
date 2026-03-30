package tech.kts.metaclass.githubmobileclient.data.auth

import tech.kts.metaclass.githubmobileclient.authLauncher

actual fun getAuthLauncher(): AuthLauncher = authLauncher!!