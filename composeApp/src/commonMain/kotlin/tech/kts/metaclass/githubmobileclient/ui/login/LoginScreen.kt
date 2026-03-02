package tech.kts.metaclass.githubmobileclient.ui.login

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun LoginScreen(
    onLoginClick: (email: String, password: String) -> Unit
) {

}

@Composable
@Preview
private fun LoginScreenPreview() {
    LoginScreen { _, _ -> }
}