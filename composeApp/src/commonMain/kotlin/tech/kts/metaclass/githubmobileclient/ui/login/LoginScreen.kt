package tech.kts.metaclass.githubmobileclient.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import githubmobileclient.composeapp.generated.resources.Res
import githubmobileclient.composeapp.generated.resources.login_hide_password_button_text
import githubmobileclient.composeapp.generated.resources.login_password_hint
import githubmobileclient.composeapp.generated.resources.login_primary_button_text
import githubmobileclient.composeapp.generated.resources.login_show_password_button_text
import githubmobileclient.composeapp.generated.resources.login_username_hint
import org.jetbrains.compose.resources.stringResource
import tech.kts.metaclass.githubmobileclient.utils.Dimens

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel{ LoginViewModel() },
    onNavigateToMain: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when(event) {
                is LoginUiEvent.LoginSuccessEvent -> onNavigateToMain()
            }
        }
    }

    LoginView(
        state = state,
        onUsernameChange = viewModel::onUsernameChange,
        onPasswordChange = viewModel::onPasswordChange,
        onPasswordVisibilityClick = viewModel::onPasswordVisibilityClick,
        onLoginClick = viewModel::onLoginClick
    )
}

@Composable
private fun LoginView(
    state: LoginUiState,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onPasswordVisibilityClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primaryContainer)
            .fillMaxSize()
            .padding(Dimens.padding),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        OutlinedTextField(
            value = state.username,
            onValueChange = onUsernameChange,
            label = { Text(stringResource(Res.string.login_username_hint)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            ),
            modifier = Modifier.fillMaxWidth(),
            isError = state.error
        )

        Spacer(modifier = Modifier.height(Dimens.gapSmall))

        OutlinedTextField(
            value = state.password,
            onValueChange = onPasswordChange,
            label = { Text(stringResource(Res.string.login_password_hint)) },
            singleLine = true,
            visualTransformation = if (state.isPasswordVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            trailingIcon = {
                TextButton(onClick = onPasswordVisibilityClick) {
                    Text(
                        if (state.isPasswordVisible) {
                            stringResource(Res.string.login_hide_password_button_text)
                        } else {
                            stringResource(Res.string.login_show_password_button_text)
                        }
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(Dimens.gapMedium))

        Button(
            onClick = onLoginClick,
            modifier = Modifier.fillMaxWidth(),
            enabled = state.isLoginButtonActive
        ) {
            Text(stringResource(Res.string.login_primary_button_text))
        }
    }
}