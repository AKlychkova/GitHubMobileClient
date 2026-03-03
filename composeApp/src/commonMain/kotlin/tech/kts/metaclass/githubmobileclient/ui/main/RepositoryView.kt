package tech.kts.metaclass.githubmobileclient.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import githubmobileclient.composeapp.generated.resources.GitHub_Invertocat_Black
import githubmobileclient.composeapp.generated.resources.Res
import githubmobileclient.composeapp.generated.resources.main_avatar_description
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import tech.kts.metaclass.githubmobileclient.entities.GitHubRepository
import tech.kts.metaclass.githubmobileclient.entities.Owner
import tech.kts.metaclass.githubmobileclient.entities.ProgrammingLanguage
import tech.kts.metaclass.githubmobileclient.utils.Dimens

@Composable
fun RepositoryView(repository: GitHubRepository) {
    Card(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(horizontal = Dimens.padding)
            .clip(RoundedCornerShape(Dimens.roundCorner))
            .background(MaterialTheme.colorScheme.surfaceContainerHigh)

    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .wrapContentSize()
                .padding(Dimens.padding)
        ) {
            AsyncImage(
                model = repository.owner.avatarUrl,
                contentDescription = stringResource(Res.string.main_avatar_description),
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(Dimens.roundCorner)),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(Res.drawable.GitHub_Invertocat_Black)
            )
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(start = Dimens.padding)
            ) {
                Text(
                    text = repository.name,
                    fontWeight = FontWeight.Bold
                )
                Text('@' + repository.owner.username)
                Spacer(Modifier.height(Dimens.gapSmall))
                Text(repository.description)
            }
        }
    }
}

@Preview
@Composable
private fun RepositoryViewPreview() {
    RepositoryView(
        GitHubRepository(
            name = "My first repository",
            description = "Test project",
            language = ProgrammingLanguage.KOTLIN,
            stars = 5,
            owner = Owner(
                username = "AKlychkova",
                avatarUrl = "https://avatars.githubusercontent.com/u/90353866?v=4"
            )
        )
    )
}