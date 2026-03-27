package tech.kts.metaclass.githubmobileclient.data.network.auth

sealed class AuthResult {
    data class Success(val code: String, val codeVerifier: String?) : AuthResult()
    data class Error(val cause: Throwable) : AuthResult()
}
