package tech.kts.metaclass.githubmobileclient.ui.screens.login

import androidx.compose.runtime.Immutable

@Immutable
data class LoginUiState(
    val username: String,
    val password: String,
    val isLoginButtonActive: Boolean,
    val isPasswordVisible: Boolean,
    val error: Boolean
) {
    companion object {
        val Initial = LoginUiState(
            username = "",
            password = "",
            error = false,
            isLoginButtonActive = false,
            isPasswordVisible = false
        )
    }
}
