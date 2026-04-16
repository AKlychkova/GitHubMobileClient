package tech.kts.metaclass.githubmobileclient.ui.views

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import tech.kts.metaclass.githubmobileclient.ui.theme.GitHubMaterialTheme
import tech.kts.metaclass.githubmobileclient.ui.theme.repositoryShimmerHeight

@Composable
fun RepositoryShimmer(
    modifier: Modifier = Modifier
) {
    Shimmer(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
            .fillMaxWidth()
            .height(repositoryShimmerHeight)
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