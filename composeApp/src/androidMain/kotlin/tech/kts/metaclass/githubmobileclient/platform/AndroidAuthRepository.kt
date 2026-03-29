package tech.kts.metaclass.githubmobileclient.platform

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.suspendCancellableCoroutine
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.AuthorizationService
import net.openid.appauth.AuthorizationServiceConfiguration
import net.openid.appauth.ClientSecretPost
import net.openid.appauth.ResponseTypeValues
import net.openid.appauth.TokenRequest
import tech.kts.metaclass.githubmobileclient.data.network.auth.AuthConfig
import tech.kts.metaclass.githubmobileclient.data.repositories.TokenRepository
import tech.kts.metaclass.githubmobileclient.utils.runSuspendCatching
import kotlin.coroutines.resume

class AndroidAuthRepository(
    private val tokenRepository: TokenRepository,
    private val config: AuthConfig,
    private val context: Context
) : AuthRepository {

    private val authService = AuthorizationService(context)
    private val tokenRequestChannel = Channel<Result<TokenRequest>>(capacity = 1)
    private var launcher: ActivityResultLauncher<Intent>? = null

    fun registerCaller(caller: ActivityResultCaller) {
        launcher = caller.registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            val intent = result.data ?: return@registerForActivityResult
            handleAuthResponseIntent(intent)
        }
    }

    override suspend fun login(): Result<Unit> = runSuspendCatching {
        val request = getCode().getOrThrow()
        val token = exchangeCodeForToken(request).getOrThrow()
        tokenRepository.setToken(token).getOrThrow()
    }

    override suspend fun logout() {
        tokenRepository.clearToken()
    }

    fun dispose() {
        authService.dispose()
        tokenRequestChannel.close()
    }

    private suspend fun getCode(): Result<TokenRequest> {
        val request = getAuthRequest()
        val customTabsIntent = CustomTabsIntent.Builder().build()
        val openAuthPageIntent = authService.getAuthorizationRequestIntent(
            request,
            customTabsIntent
        )
        launcher?.launch(openAuthPageIntent) ?: return Result.failure(IllegalStateException("Launcher has not been assigned"))

        return tokenRequestChannel.receive()
    }

    private suspend fun exchangeCodeForToken(
        request: TokenRequest
    ): Result<String> = suspendCancellableCoroutine { cont ->
        authService.performTokenRequest(
            request,
            ClientSecretPost(config.clientSecret)
        ) { response, ex ->
            when {
                response != null -> {
                    val accessToken = response.accessToken.orEmpty()
                    cont.resume(Result.success(accessToken))
                }

                ex != null -> { cont.resume(Result.failure(ex)) }
            }
        }
    }

    private fun getAuthRequest(): AuthorizationRequest {
        val serviceConfig = AuthorizationServiceConfiguration(
            (config.authUri).toUri(),
            (config.tokenUri).toUri()
        )
        val redirectUri = config.callbackUrl.toUri()

        return AuthorizationRequest.Builder(
            serviceConfig,
            config.clientId,
            ResponseTypeValues.CODE,
            redirectUri
        )
            .setScopes(config.scopes)
            .build()
    }

    private fun handleAuthResponseIntent(intent: Intent) {
        val response  = AuthorizationResponse.fromIntent(intent)
        val exception = AuthorizationException.fromIntent(intent)

        tokenRequestChannel.trySend(
            when {
                response != null -> Result.success(response.createTokenExchangeRequest())
                exception != null -> Result.failure(exception)
                else -> Result.failure(IllegalStateException("No code or error in response"))
            }
        )
    }
}