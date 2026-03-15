package tech.kts.metaclass.githubmobileclient.data.auth

interface AuthLauncher {
    suspend fun launch(config: AuthConfig): AuthResult
}

expect fun getAuthLauncher(): AuthLauncher