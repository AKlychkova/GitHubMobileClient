package tech.kts.metaclass.githubmobileclient.ui.screens.start

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import githubmobileclient.composeapp.generated.resources.Res
import githubmobileclient.composeapp.generated.resources.start_primary_button
import githubmobileclient.composeapp.generated.resources.start_subtitle
import githubmobileclient.composeapp.generated.resources.start_title
import org.jetbrains.compose.resources.stringResource
import tech.kts.metaclass.githubmobileclient.ui.theme.GitHubMaterialTheme
import tech.kts.metaclass.githubmobileclient.ui.theme.gapLarge
import tech.kts.metaclass.githubmobileclient.ui.theme.gapMedium
import tech.kts.metaclass.githubmobileclient.ui.theme.gapSmall
import tech.kts.metaclass.githubmobileclient.ui.theme.paddingMedium

@Composable
fun StartScreen(
    viewModel: StartViewModel = viewModel { StartViewModel() },
    onNavigateToLogin: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(horizontal = paddingMedium)
                .padding(contentPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(Modifier.height(gapLarge))
            Text(
                stringResource(Res.string.start_title),
                style = MaterialTheme.typography.displaySmall,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(gapMedium))
            Text(
                stringResource(Res.string.start_subtitle),
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    viewModel.disableStartScreen()
                    onNavigateToLogin()
                },
                modifier = Modifier
                    .fillMaxWidth(),
                shape = MaterialTheme.shapes.small
            ) {
                Text(
                    stringResource(Res.string.start_primary_button)
                )
            }
            Spacer(modifier = Modifier.height(gapSmall))
        }
    }
}

@Composable
@Preview
private fun StartScreenPreview() {
    GitHubMaterialTheme {
        StartScreen {}
    }
}

@Composable
@Preview
private fun StartScreenPreviewDark() {
    GitHubMaterialTheme(darkTheme = true) {
        StartScreen {}
    }
}