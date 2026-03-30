package tech.kts.metaclass.githubmobileclient.ui.screens.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import githubmobileclient.composeapp.generated.resources.GitHub_Invertocat_Black
import githubmobileclient.composeapp.generated.resources.Res
import githubmobileclient.composeapp.generated.resources.login_hide_password_button
import githubmobileclient.composeapp.generated.resources.login_image_description
import githubmobileclient.composeapp.generated.resources.login_password_hint
import githubmobileclient.composeapp.generated.resources.login_primary_button
import githubmobileclient.composeapp.generated.resources.login_show_password_button
import githubmobileclient.composeapp.generated.resources.login_title
import githubmobileclient.composeapp.generated.resources.login_username_hint
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import tech.kts.metaclass.githubmobileclient.ui.theme.GitHubMaterialTheme
import tech.kts.metaclass.githubmobileclient.ui.theme.gapLarge
import tech.kts.metaclass.githubmobileclient.ui.theme.gapMedium
import tech.kts.metaclass.githubmobileclient.ui.theme.gapSmall
import tech.kts.metaclass.githubmobileclient.ui.theme.iconPadding
import tech.kts.metaclass.githubmobileclient.ui.theme.paddingMedium

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel { LoginViewModel() },
    modifier: Modifier = Modifier,
    onNavigateToMain: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is LoginUiEvent.LoginSuccessEvent -> onNavigateToMain()
            }
        }
    }

    LoginView(
        state = state,
        onUsernameChange = viewModel::onUsernameChange,
        onPasswordChange = viewModel::onPasswordChange,
        onPasswordVisibilityClick = viewModel::onPasswordVisibilityClick,
        onLoginClick = viewModel::onLoginClick,
        modifier = modifier
    )
}

@Composable
private fun LoginView(
    state: LoginUiState,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onPasswordVisibilityClick: () -> Unit,
    onLoginClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(horizontal = paddingMedium)
                .padding(contentPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(gapLarge))

            Icon(
                painter = painterResource(Res.drawable.GitHub_Invertocat_Black),
                contentDescription = stringResource(Res.string.login_image_description),
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .size(64.dp)
                    .padding(iconPadding)
                    .aspectRatio(1f)
            )

            Spacer(modifier = Modifier.height(gapMedium))

            Text(
                stringResource(Res.string.login_title),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(gapMedium))

            OutlinedTextField(
                value = state.username,
                onValueChange = onUsernameChange,
                label = { Text(stringResource(Res.string.login_username_hint)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                isError = state.error
            )

            Spacer(modifier = Modifier.height(gapSmall))

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
                                stringResource(Res.string.login_hide_password_button)
                            } else {
                                stringResource(Res.string.login_show_password_button)
                            }
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(gapMedium))

            Button(
                onClick = onLoginClick,
                modifier = Modifier.fillMaxWidth(),
                enabled = state.isLoginButtonActive,
                shape = MaterialTheme.shapes.small
            ) {
                Text(stringResource(Res.string.login_primary_button))
            }
        }
    }
}

@Composable
@Preview
private fun LoginScreenPreview() {
    var state by remember { mutableStateOf(LoginUiState.Initial) }
    GitHubMaterialTheme {
        LoginView(
            state = state,
            onUsernameChange = { state = state.copy(username = it) },
            onPasswordChange = { state = state.copy(password = it) },
            onPasswordVisibilityClick = {
                state = state.copy(isPasswordVisible = !state.isPasswordVisible)
            },
            onLoginClick = {}
        )
    }
}


@Composable
@Preview
private fun LoginScreenPreviewDark() {
    var state by remember { mutableStateOf(LoginUiState.Initial) }
    GitHubMaterialTheme(darkTheme = true) {
        LoginView(
            state = state,
            onUsernameChange = { state = state.copy(username = it) },
            onPasswordChange = { state = state.copy(password = it) },
            onPasswordVisibilityClick = {
                state = state.copy(isPasswordVisible = !state.isPasswordVisible)
            },
            onLoginClick = {}
        )
    }
}