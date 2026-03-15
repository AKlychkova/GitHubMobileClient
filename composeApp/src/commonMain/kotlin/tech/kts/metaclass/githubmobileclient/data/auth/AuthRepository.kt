package tech.kts.metaclass.githubmobileclient.data.auth

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.Parameters
import tech.kts.metaclass.githubmobileclient.data.network.Network
import tech.kts.metaclass.githubmobileclient.utils.runSuspendCatching

class AuthRepository(
    private val config: AuthConfig = GitHubAuthConfig,
    private val launcher: AuthLauncher = getAuthLauncher(),
    private val httpClient: HttpClient = Network.authHttpClient
) {

    suspend fun login(): Result<Unit> {
        return when (val result = launcher.launch(config)) {
            is AuthResult.Success -> exchangeCodeForToken(result.code, result.codeVerifier)
            is AuthResult.Error -> Result.failure(result.cause)
        }
    }

    fun logout() {
        TokenStorage.remove()
    }

    private suspend fun exchangeCodeForToken(code: String, verifier: String?) = runSuspendCatching {
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
        TokenStorage.set(token)
    }
}