package tech.kts.metaclass.githubmobileclient.data.auth

import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.AuthorizationService
import net.openid.appauth.AuthorizationServiceConfiguration
import net.openid.appauth.ResponseTypeValues
import kotlin.coroutines.resume

class AndroidAuthLauncher(
    activity: ComponentActivity
) : AuthLauncher {
    private val authService = AuthorizationService(activity)
    private var continuation: CancellableContinuation<AuthResult>? = null
    private val launcher = activity.registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val cont = continuation ?: return@registerForActivityResult
        val data = result.data ?: return@registerForActivityResult
        val response = AuthorizationResponse.fromIntent(data)
        val exception = AuthorizationException.fromIntent(data)
        when {
            response != null -> {
                val request = response.createTokenExchangeRequest()
                val code = request.authorizationCode
                if (code != null) {
                    cont.resume(AuthResult.Success(code, request.codeVerifier))
                } else {
                    cont.resume(AuthResult.Error(IllegalStateException("No authorization code")))
                }
            }

            exception != null -> cont.resume(AuthResult.Error(exception))
        }
    }

    override suspend fun launch(config: AuthConfig): AuthResult =
        suspendCancellableCoroutine { cont ->
            continuation = cont

            val request = getAuthRequest(config)
            val customTabsIntent = CustomTabsIntent.Builder().build()
            val openAuthPageIntent = authService.getAuthorizationRequestIntent(
                request,
                customTabsIntent
            )

            launcher.launch(openAuthPageIntent)
        }

    fun dispose() {
        authService.dispose()
    }

    private fun getAuthRequest(config: AuthConfig): AuthorizationRequest {
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
}