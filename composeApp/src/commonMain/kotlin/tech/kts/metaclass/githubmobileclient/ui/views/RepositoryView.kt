package tech.kts.metaclass.githubmobileclient.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import githubmobileclient.composeapp.generated.resources.issue_opened
import githubmobileclient.composeapp.generated.resources.repo_forked
import githubmobileclient.composeapp.generated.resources.Res
import githubmobileclient.composeapp.generated.resources.star
import githubmobileclient.composeapp.generated.resources.repo_forks_icon_content_description
import githubmobileclient.composeapp.generated.resources.repo_issues_icon_content_description
import githubmobileclient.composeapp.generated.resources.repo_stars_icon_content_description
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import tech.kts.metaclass.githubmobileclient.entities.ProgrammingLanguage
import tech.kts.metaclass.githubmobileclient.ui.screens.search.RepositoryUiState
import tech.kts.metaclass.githubmobileclient.ui.theme.GitHubMaterialTheme
import tech.kts.metaclass.githubmobileclient.ui.theme.avatarSize
import tech.kts.metaclass.githubmobileclient.ui.theme.iconTitleSpace
import tech.kts.metaclass.githubmobileclient.ui.theme.languageIconSize
import tech.kts.metaclass.githubmobileclient.ui.theme.paddingSmall
import tech.kts.metaclass.githubmobileclient.ui.theme.repoCardHorizontalSpace
import tech.kts.metaclass.githubmobileclient.ui.theme.repoCardVerticalSpace
import tech.kts.metaclass.githubmobileclient.ui.theme.repoInfoIconSize
import tech.kts.metaclass.githubmobileclient.ui.theme.spaceBetweenRepoInfoIcons

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun RepositoryView(
    repository: RepositoryUiState,
    modifier: Modifier = Modifier
) {
    OutlinedCard(
        modifier = modifier
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(repoCardVerticalSpace),
            modifier = Modifier
                .wrapContentSize()
                .padding(paddingSmall)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(repoCardHorizontalSpace)
            ) {
                Avatar(
                    url = repository.avatarUrl,
                    modifier = Modifier
                        .size(avatarSize)
                        .aspectRatio(1f)
                )
                Text(
                    text = repository.fullName,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .weight(1f)
                )
                InfoChip(
                    text = repository.visibility
                )
            }
            if (repository.description != null) {
                Text(
                    text = repository.description,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(iconTitleSpace)
            ) {
                LanguageIcon(
                    language = repository.language,
                    modifier = Modifier
                        .size(languageIconSize)
                        .aspectRatio(1f)
                )
                Text(
                    text = repository.language.title,
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(Modifier.width(spaceBetweenRepoInfoIcons))
                Icon(
                    painter = painterResource(Res.drawable.repo_forked),
                    contentDescription = stringResource(Res.string.repo_forks_icon_content_description),
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(repoInfoIconSize)
                )
                Text(
                    text = repository.forks,
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(Modifier.width(spaceBetweenRepoInfoIcons))
                Icon(
                    painter = painterResource(Res.drawable.star),
                    contentDescription = stringResource(Res.string.repo_stars_icon_content_description),
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(repoInfoIconSize)
                )
                Text(
                    text = repository.stars,
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(Modifier.width(spaceBetweenRepoInfoIcons))
                Icon(
                    painter = painterResource(Res.drawable.issue_opened),
                    contentDescription = stringResource(Res.string.repo_issues_icon_content_description),
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(repoInfoIconSize)
                )
                Text(
                    text = repository.openIssues,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Preview
@Composable
private fun RepositoryViewPreviewLight() {
    GitHubMaterialTheme {
        RepositoryView(
            RepositoryUiState(
                id = 1,
                fullName = "Octocat/MyFirstProject",
                description = "A modern, lightweight library designed to simplify development of scalable and maintainable applications using Kotlin Multiplatform. It includes support for networking, local caching, state management, and follows clean architecture principles, making it easy to share business logic across Android and iOS while keeping UI layers independent and responsive.",
                language = ProgrammingLanguage.KOTLIN,
                stars = "5.1k",
                visibility = "Public",
                forks = "3M",
                openIssues = "1",
                avatarUrl = "https://avatars.githubusercontent.com/u/14364638?s=48&v=4"
            )
        )
    }
}

@Preview
@Composable
private fun RepositoryViewPreviewDark() {
    GitHubMaterialTheme(darkTheme = true) {
        RepositoryView(
            RepositoryUiState(
                id = 1,
                fullName = "Octocat/MyFirstProject",
                description = "A modern, lightweight library designed to simplify development of scalable and maintainable applications using Kotlin Multiplatform. It includes support for networking, local caching, state management, and follows clean architecture principles, making it easy to share business logic across Android and iOS while keeping UI layers independent and responsive.",
                language = ProgrammingLanguage.KOTLIN,
                stars = "5.1k",
                visibility = "Public",
                forks = "3M",
                openIssues = "1",
                avatarUrl = "https://avatars.githubusercontent.com/u/14364638?s=48&v=4"
            )
        )
    }
}