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
import androidx.compose.material3.OutlinedCard
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
        initialValue = 0.6f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 900,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )

    OutlinedCard(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(repositoryShimmerHeight)
                .graphicsLayer { this.alpha = alpha }
                .background(MaterialTheme.colorScheme.surfaceVariant)
        )
    }
}

@Composable
@Preview
fun RepositoryShimmerPreview() {
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