package tech.kts.metaclass.githubmobileclient.useCases.auth

interface TokenRepository{
    suspend fun setToken(token: String): Result<Unit>
    suspend fun getToken(): String?
    suspend fun clearToken(): Result<Unit>
}