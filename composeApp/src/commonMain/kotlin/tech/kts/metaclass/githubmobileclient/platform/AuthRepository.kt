package tech.kts.metaclass.githubmobileclient.platform

interface AuthRepository {
    suspend fun login(): Result<Unit>
    suspend fun logout()
}