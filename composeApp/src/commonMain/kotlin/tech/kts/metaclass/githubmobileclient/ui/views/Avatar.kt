package tech.kts.metaclass.githubmobileclient.ui.views

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import githubmobileclient.composeapp.generated.resources.Res
import githubmobileclient.composeapp.generated.resources.avatar_description
import io.github.aakira.napier.Napier
import org.jetbrains.compose.resources.stringResource

@Composable
fun Avatar(
    url: String?,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalPlatformContext.current)
            .data(url)
            .listener(
                onError = { _, result ->
                    Napier.e("Image loading error: ${result.throwable}")
                }
            )
            .build(),
        contentDescription = stringResource(Res.string.avatar_description),
        modifier = modifier
            .clip(CircleShape),
        contentScale = ContentScale.Crop,
        placeholder = ColorPainter(MaterialTheme.colorScheme.surfaceVariant)
    )
}

@Preview
@Composable
private fun AvatarPreview() {
    Avatar(
        url = "https://avatars.githubusercontent.com/u/14364638?s=48&v=4"
    )
}