package tech.kts.metaclass.githubmobileclient.ui.views

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import tech.kts.metaclass.githubmobileclient.ui.theme.GitHubMaterialTheme
import tech.kts.metaclass.githubmobileclient.ui.theme.repositoryShimmerHeight

@Composable
fun RepositoryShimmer(
    modifier: Modifier = Modifier
) {
    val transition = rememberInfiniteTransition()

    val alpha by transition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )


    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(repositoryShimmerHeight)
            .graphicsLayer { this.alpha = alpha }
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = MaterialTheme.shapes.medium
            )
    )
}

@Composable
@Preview
private fun RepositoryShimmerPreviewLight() {
    GitHubMaterialTheme {
        RepositoryShimmer()
    }
}

@Composable
@Preview
fun RepositoryShimmerPreviewDark() {
    GitHubMaterialTheme(darkTheme = true) {
        RepositoryShimmer()
    }
}