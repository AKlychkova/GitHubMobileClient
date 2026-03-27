package tech.kts.metaclass.githubmobileclient.platform

import tech.kts.metaclass.githubmobileclient.data.network.auth.AuthConfig
import tech.kts.metaclass.githubmobileclient.data.network.auth.AuthResult

interface AuthLauncher {
    suspend fun launch(config: AuthConfig): AuthResult
}

expect fun getAuthLauncher(): AuthLauncher