package tech.kts.metaclass.githubmobileclient.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import githubmobileclient.composeapp.generated.resources.GitHub_Invertocat_Black
import githubmobileclient.composeapp.generated.resources.Res
import githubmobileclient.composeapp.generated.resources.main_avatar_description
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import tech.kts.metaclass.githubmobileclient.entities.GitHubRepository
import tech.kts.metaclass.githubmobileclient.entities.User
import tech.kts.metaclass.githubmobileclient.entities.ProgrammingLanguage
import tech.kts.metaclass.githubmobileclient.ui.theme.gapSmall
import tech.kts.metaclass.githubmobileclient.ui.theme.paddingMedium

// TODO: Refactor view
@Composable
fun RepositoryView(
    repository: GitHubRepository,
    modifier: Modifier = Modifier
) {
    OutlinedCard(
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .wrapContentSize()
                .padding(paddingMedium)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalPlatformContext.current)
                    .data(repository.owner.avatarUrl)
                    .listener(
                        onError = { _, result ->
                            println("Image error: ${result.throwable}")
                        }
                    )
                    .build(),
                contentDescription = stringResource(Res.string.main_avatar_description),
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .aspectRatio(1f)
                    .clip(MaterialTheme.shapes.medium),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(Res.drawable.GitHub_Invertocat_Black)
            )
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(start = paddingMedium)
            ) {
                Text(
                    text = repository.name,
                    fontWeight = FontWeight.Bold
                )
                Text('@' + repository.owner.username)
                Spacer(Modifier.height(gapSmall))
                Text(repository.description ?: "")
            }
        }
    }
}

@Preview
@Composable
private fun RepositoryViewPreview() {
    RepositoryView(
        GitHubRepository(
            id = 1,
            name = "My first repository",
            description = "Test project",
            language = ProgrammingLanguage.KOTLIN,
            stars = 5,
            owner = User(
                id = 1L,
                username = "AKlychkova",
                avatarUrl = "https://avatars.githubusercontent.com/u/90353866?v=4"
            )
        )
    )
}