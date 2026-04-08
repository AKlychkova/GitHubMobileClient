package tech.kts.metaclass.githubmobileclient.ui.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import githubmobileclient.composeapp.generated.resources.GitHub_Invertocat_Black
import githubmobileclient.composeapp.generated.resources.Res
import githubmobileclient.composeapp.generated.resources.login_image_description
import githubmobileclient.composeapp.generated.resources.login_primary_button
import githubmobileclient.composeapp.generated.resources.login_subtitle
import githubmobileclient.composeapp.generated.resources.login_title
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import tech.kts.metaclass.githubmobileclient.ui.theme.GitHubMaterialTheme
import tech.kts.metaclass.githubmobileclient.ui.theme.gapLarge
import tech.kts.metaclass.githubmobileclient.ui.theme.gapMedium
import tech.kts.metaclass.githubmobileclient.ui.theme.gapSmall
import tech.kts.metaclass.githubmobileclient.ui.theme.iconPadding
import tech.kts.metaclass.githubmobileclient.ui.theme.paddingMedium
import tech.kts.metaclass.githubmobileclient.ui.views.LoginFailureDialog

@Composable
fun LoginScreen(
    onNavigateToMain: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = koinViewModel<LoginViewModel>()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var showFailureDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is LoginUiEvent.LoginSuccessEvent -> onNavigateToMain()
                is LoginUiEvent.LoginFailureEvent -> showFailureDialog = true
            }
        }
    }

    LoginView(
        state = state,
        onLoginClick = viewModel::onLoginClick,
        modifier = modifier
    )

    if (showFailureDialog) {
        LoginFailureDialog(
            onDismissRequest = { showFailureDialog = false }
        )
    }
}

@Composable
private fun LoginView(
    state: LoginUiState,
    onLoginClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { contentPadding ->
        if (state.isLoading) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(64.dp)
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(horizontal = paddingMedium)
                    .padding(contentPadding)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
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

                Spacer(Modifier.height(gapLarge))
                Text(
                    stringResource(Res.string.login_title),
                    style = MaterialTheme.typography.displaySmall,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(gapMedium))
                Text(
                    stringResource(Res.string.login_subtitle),
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = onLoginClick,
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        stringResource(Res.string.login_primary_button)
                    )
                }
                Spacer(modifier = Modifier.height(gapSmall))
            }
        }
    }
}

@Composable
@Preview
private fun LoginScreenPreview() {
    var state by remember { mutableStateOf(LoginUiState()) }
    GitHubMaterialTheme {
        LoginView(
            state = state,
            onLoginClick = {}
        )
    }
}


@Composable
@Preview
private fun LoginScreenPreviewDark() {
    var state by remember { mutableStateOf(LoginUiState()) }
    GitHubMaterialTheme(darkTheme = true) {
        LoginView(
            state = state,
            onLoginClick = {}
        )
    }
}