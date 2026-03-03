package tech.kts.metaclass.githubmobileclient.ui.start

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import githubmobileclient.composeapp.generated.resources.GitHub_Invertocat_Black
import githubmobileclient.composeapp.generated.resources.Res
import githubmobileclient.composeapp.generated.resources.start_image_description
import githubmobileclient.composeapp.generated.resources.start_primary_button_text
import githubmobileclient.composeapp.generated.resources.start_welcome_text
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import tech.kts.metaclass.githubmobileclient.utils.Dimens

@Composable
fun StartScreen(
    onNavigateToLogin: () -> Unit
) {
    MaterialTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .fillMaxSize()
                .padding(Dimens.padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(Res.drawable.GitHub_Invertocat_Black),
                contentDescription = stringResource(Res.string.start_image_description),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimens.padding)
                    .aspectRatio(1f),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.height(Dimens.gapLarge))
            Text(
                stringResource(Res.string.start_welcome_text),
                fontSize = Dimens.fontLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(Dimens.gapLarge))
            Button(
                onClick = onNavigateToLogin,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    stringResource(Res.string.start_primary_button_text),
                    fontSize = Dimens.fontMedium
                )
            }
        }
    }
}

@Composable
@Preview
private fun StartScreenPreview() {
    StartScreen {}
}