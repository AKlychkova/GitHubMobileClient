package tech.kts.metaclass.githubmobileclient.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.ui.unit.dp
import githubmobileclient.composeapp.generated.resources.Res
import githubmobileclient.composeapp.generated.resources.error
import githubmobileclient.composeapp.generated.resources.error_primary_button
import githubmobileclient.composeapp.generated.resources.error_title
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import tech.kts.metaclass.githubmobileclient.ui.theme.GitHubMaterialTheme
import tech.kts.metaclass.githubmobileclient.ui.theme.gapSmall
import tech.kts.metaclass.githubmobileclient.ui.theme.paddingSmall

@Composable
fun Error(
    modifier: Modifier = Modifier,
    onRetryClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Spacer(Modifier.weight(1f))
        Image(
            painter = painterResource(Res.drawable.error),
            contentDescription = null,
            modifier = Modifier
                .padding(horizontal = 64.dp, vertical = 16.dp)
                .fillMaxWidth()
                .aspectRatio(1f)
        )
        Text(
            text = stringResource(Res.string.error_title),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(Modifier.weight(1f))
        Button(
            onClick = onRetryClick,
            modifier = Modifier
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.small
        ) {
            Text(
                stringResource(Res.string.error_primary_button)
            )
        }
        Spacer(modifier = Modifier.height(gapSmall))
    }
}

@Preview
@Composable
private fun ErrorPreview() {
    GitHubMaterialTheme {
        Scaffold {
            Error(
                onRetryClick = { },
                modifier = Modifier
                    .padding(paddingSmall)
                    .fillMaxSize()
            )
        }
    }
}

@Preview
@Composable
private fun DarkErrorPreview() {
    GitHubMaterialTheme(darkTheme = true) {
        Scaffold {
            Error(
                onRetryClick = { },
                modifier = Modifier
                    .padding(paddingSmall)
                    .fillMaxSize()
            )
        }
    }
}