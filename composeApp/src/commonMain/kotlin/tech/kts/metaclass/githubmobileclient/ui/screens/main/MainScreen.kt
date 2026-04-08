package tech.kts.metaclass.githubmobileclient.ui.screens.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import githubmobileclient.composeapp.generated.resources.Repo
import githubmobileclient.composeapp.generated.resources.Res
import githubmobileclient.composeapp.generated.resources.error
import githubmobileclient.composeapp.generated.resources.main_cached_data_warning
import githubmobileclient.composeapp.generated.resources.main_empty_search_warning
import githubmobileclient.composeapp.generated.resources.main_error_primary_button
import githubmobileclient.composeapp.generated.resources.main_error_title
import githubmobileclient.composeapp.generated.resources.warning
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import tech.kts.metaclass.githubmobileclient.entities.ProgrammingLanguage
import tech.kts.metaclass.githubmobileclient.ui.theme.GitHubMaterialTheme
import tech.kts.metaclass.githubmobileclient.ui.theme.gapMedium
import tech.kts.metaclass.githubmobileclient.ui.theme.gapSmall
import tech.kts.metaclass.githubmobileclient.ui.theme.iconTitleSpace
import tech.kts.metaclass.githubmobileclient.ui.theme.paddingMedium
import tech.kts.metaclass.githubmobileclient.ui.theme.paddingSmall
import tech.kts.metaclass.githubmobileclient.ui.theme.warningIconSize
import tech.kts.metaclass.githubmobileclient.ui.views.RepositoryShimmer
import tech.kts.metaclass.githubmobileclient.ui.views.RepositoryView
import tech.kts.metaclass.githubmobileclient.ui.views.SearchField

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = koinViewModel<MainViewModel>()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    MainView(
        state = state,
        onSearchQueryChange = viewModel::onSearchQueryChange,
        onClearSearch = viewModel::clearSearch,
        onSearchRetryClick = viewModel::onSearchRetry,
        modifier = modifier
    )
}

@Composable
private fun MainView(
    state: MainUiState,
    onSearchQueryChange: (String) -> Unit,
    onClearSearch: () -> Unit,
    onSearchRetryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(horizontal = paddingMedium)
                .padding(contentPadding)
                .fillMaxSize()
        ) {
            SearchField(
                searchQuery = state.searchQuery,
                onSearchQueryChange = onSearchQueryChange,
                onClearSearch = onClearSearch,
                enabled = !state.isLoading,
                modifier = Modifier
                    .padding(vertical = paddingSmall)
                    .fillMaxWidth()
            )
            if (state.isLoading) {
                Shimmers()
            } else if (state.error) {
                Error(
                    modifier = Modifier
                        .fillMaxSize(),
                    onRetryClick = onSearchRetryClick
                )
            } else if (state.searchQuery.isBlank()) {
                EmptySearch(
                    modifier = Modifier
                        .fillMaxSize()
                )
            } else {
                AnimatedVisibility(visible = state.isCachedDataShown) {
                    CachedDataWarning(
                        Modifier
                            .fillMaxWidth()
                            .padding(bottom = paddingSmall)
                    )
                }
                Repositories(state.repositories)
            }
        }
    }
}

@Composable
private fun Repositories(
    repositoriesList: List<RepositoryUiState>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(gapSmall),
        modifier = modifier
    ) {
        items(
            items = repositoriesList,
            key = { it.id }
        ) { repository ->
            RepositoryView(
                repository = repository,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
private fun Shimmers(
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(gapSmall),
        modifier = modifier
    ) {
        repeat(3) {
            RepositoryShimmer(
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun EmptySearch(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(Res.drawable.Repo),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(64.dp)
        )
        Spacer(Modifier.height(gapMedium))
        Text(
            text = stringResource(Res.string.main_empty_search_warning),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineSmall
        )
    }
}

@Composable
private fun Error(
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
            text = stringResource(Res.string.main_error_title),
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
                stringResource(Res.string.main_error_primary_button)
            )
        }
        Spacer(modifier = Modifier.height(gapSmall))
    }
}

@Composable
private fun CachedDataWarning(
    modifier: Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(iconTitleSpace),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(Res.drawable.warning),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.error,
            modifier = Modifier.size(warningIconSize)
        )
        Text(
            stringResource(Res.string.main_cached_data_warning),
            color = MaterialTheme.colorScheme.error
        )
    }
}

@Composable
@Preview
private fun MainScreenPreview(
    @PreviewParameter(MainScreenPreviewProvider::class) param: MainPreviewParameter
) {
    val repositoryMock = RepositoryUiState(
        id = 1,
        fullName = "Octocat/MyFirstProject",
        description = "A Kotlin Multiplatform library for building modern mobile apps with shared business logic and clean architecture.",
        language = ProgrammingLanguage.KOTLIN,
        stars = "5.1k",
        visibility = "Public",
        forks = "3.2M",
        openIssues = "1",
        avatarUrl = "https://avatars.githubusercontent.com/u/14364638?s=48&v=4"
    )
    val repositories = List(10) { id -> repositoryMock.copy(id = id) }
    var state by remember {
        mutableStateOf(
            MainUiState(
                repositories = repositories,
                isLoading = param.isLoading,
                searchQuery = if (param.isQueryEmpty) "" else "some query",
                isCachedDataShown = true,
                error = param.error
            )
        )
    }

    GitHubMaterialTheme(darkTheme = param.isDark) {
        MainView(
            state = state,
            onSearchQueryChange = {},
            onClearSearch = {},
            onSearchRetryClick = {}
        )
    }
}

private data class MainPreviewParameter(
    val isDark: Boolean,
    val isLoading: Boolean,
    val isQueryEmpty: Boolean,
    val error: Boolean
)

private class MainScreenPreviewProvider : PreviewParameterProvider<MainPreviewParameter> {
    override val values = sequenceOf(
        MainPreviewParameter(
            isDark = false,
            isLoading = false,
            isQueryEmpty = false,
            error = false
        ),
        MainPreviewParameter(
            isDark = true,
            isLoading = false,
            isQueryEmpty = false,
            error = false
        ),
        MainPreviewParameter(
            isDark = false,
            isLoading = true,
            isQueryEmpty = false,
            error = false
        ),
        MainPreviewParameter(
            isDark = false,
            isLoading = false,
            isQueryEmpty = true,
            error = false
        ),
        MainPreviewParameter(
            isDark = true,
            isLoading = false,
            isQueryEmpty = false,
            error = true
        )
    )
}
