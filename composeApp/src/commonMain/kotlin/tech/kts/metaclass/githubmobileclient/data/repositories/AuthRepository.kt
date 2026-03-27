package tech.kts.metaclass.githubmobileclient.data.repositories

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.Parameters
import tech.kts.metaclass.githubmobileclient.data.network.auth.AuthConfig
import tech.kts.metaclass.githubmobileclient.data.network.auth.AuthResult
import tech.kts.metaclass.githubmobileclient.data.network.auth.AuthToken
import tech.kts.metaclass.githubmobileclient.platform.AuthLauncher
import tech.kts.metaclass.githubmobileclient.utils.runSuspendCatching

// TODO вынести интерфейс в domain слой, чтобы не было зависимости domain -> data
interface AuthRepository {
    suspend fun login(): Result<Unit>
    suspend fun logout()
}

class AuthRepositoryImpl(
    private val config: AuthConfig,
    private val launcher: AuthLauncher,
    private val httpClient: HttpClient,
    private val tokenRepository: TokenRepository
): AuthRepository {

    override suspend fun login(): Result<Unit> {
        return when (val result = launcher.launch(config)) {
            is AuthResult.Success -> exchangeCodeForToken(result.code, result.codeVerifier)
            is AuthResult.Error -> Result.failure(result.cause)
        }
    }

    override suspend fun logout() {
        tokenRepository.clearToken()
    }

    private suspend fun exchangeCodeForToken(code: String, verifier: String?): Result<Unit> =
        runSuspendCatching {
            val token = httpClient.post(config.tokenUri) {
                header("Accept", "application/json")
                setBody(FormDataContent(Parameters.build {
                    append("client_id", config.clientId)
                    append("client_secret", config.clientSecret)
                    append("code", code)
                    append("redirect_uri", config.callbackUrl)
                    verifier?.let { append("code_verifier", it) }
                }))
            }.body<AuthToken>()
            tokenRepository.setToken(token.accessToken)
        }
}