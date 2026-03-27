package tech.kts.metaclass.githubmobileclient.platform

import tech.kts.metaclass.githubmobileclient.data.network.auth.AuthConfig
import tech.kts.metaclass.githubmobileclient.data.network.auth.AuthResult

class IosAuthLauncher : AuthLauncher {
    override suspend fun launch(config: AuthConfig): AuthResult {
        TODO("Not yet implemented")
    }
}