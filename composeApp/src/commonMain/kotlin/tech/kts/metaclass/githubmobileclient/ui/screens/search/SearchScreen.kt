package tech.kts.metaclass.githubmobileclient.ui.screens.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import githubmobileclient.composeapp.generated.resources.repo
import githubmobileclient.composeapp.generated.resources.Res
import githubmobileclient.composeapp.generated.resources.search_cached_data_warning
import githubmobileclient.composeapp.generated.resources.search_empty_search_warning
import githubmobileclient.composeapp.generated.resources.search_nothing_found_warning
import githubmobileclient.composeapp.generated.resources.search_nothing_found_warning_subtitle
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import tech.kts.metaclass.githubmobileclient.entities.ProgrammingLanguage
import tech.kts.metaclass.githubmobileclient.ui.theme.GitHubMaterialTheme
import tech.kts.metaclass.githubmobileclient.ui.theme.gapMedium
import tech.kts.metaclass.githubmobileclient.ui.theme.gapSmall
import tech.kts.metaclass.githubmobileclient.ui.theme.paddingMedium
import tech.kts.metaclass.githubmobileclient.ui.theme.paddingSmall
import tech.kts.metaclass.githubmobileclient.ui.utils.InfiniteListHandler
import tech.kts.metaclass.githubmobileclient.ui.views.Error
import tech.kts.metaclass.githubmobileclient.ui.views.RepositoryShimmer
import tech.kts.metaclass.githubmobileclient.ui.views.RepositoryView
import tech.kts.metaclass.githubmobileclient.ui.views.SearchField
import tech.kts.metaclass.githubmobileclient.ui.views.WarningLabel

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = koinViewModel<SearchViewModel>()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    SearchView(
        state = state,
        onSearchQueryChange = viewModel::onSearchQueryChange,
        onClearSearch = viewModel::clearSearch,
        onSearchRetry = viewModel::onSearchRetry,
        onLoadNextPage = viewModel::loadNextPage,
        modifier = modifier
    )
}

@Composable
private fun SearchView(
    state: SearchUiState,
    onSearchQueryChange: (String) -> Unit,
    onClearSearch: () -> Unit,
    onSearchRetry: () -> Unit,
    onLoadNextPage: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { contentPadding ->
        PullToRefreshBox(
            isRefreshing = false,
            onRefresh = onSearchRetry,
            modifier = Modifier.fillMaxSize()
        ) {
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
                    modifier = Modifier
                        .padding(vertical = paddingSmall)
                        .fillMaxWidth()
                )
                when (val listState = state.listState) {
                    is ListUiState.Error -> Error(
                        modifier = Modifier.fillMaxSize(),
                        onRetryClick = onSearchRetry
                    )

                    is ListUiState.Loading -> Shimmers(
                        modifier = Modifier.fillMaxSize()
                    )

                    is ListUiState.DataShown -> {
                        if (state.searchQuery.isBlank()) {
                            EmptySearch(Modifier.fillMaxSize())
                        } else if (listState.repositories.isEmpty()) {
                            NothingFound(Modifier.fillMaxSize())
                        } else {
                            AnimatedVisibility(visible = listState.isCachedDataShown) {
                                WarningLabel(
                                    text = stringResource(Res.string.search_cached_data_warning),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = paddingSmall)
                                )
                            }
                            Repositories(
                                repositoriesList = listState.repositories,
                                isLoadingNextPage = listState.isLoadingNextPage,
                                onLoadNextPage = onLoadNextPage,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun Repositories(
    repositoriesList: List<RepositoryUiState>,
    isLoadingNextPage: Boolean,
    onLoadNextPage: () -> Unit,
    modifier: Modifier = Modifier
) {

    val listState = rememberLazyListState()
    LazyColumn(
        state = listState,
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
        if (isLoadingNextPage) {
            item {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = paddingSmall)
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }

    InfiniteListHandler(listState = listState, buffer = 0, onLoadMore = onLoadNextPage)
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
            painter = painterResource(Res.drawable.repo),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(64.dp)
        )
        Spacer(Modifier.height(gapMedium))
        Text(
            text = stringResource(Res.string.search_empty_search_warning),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineSmall
        )
    }
}

@Composable
private fun NothingFound(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(Res.drawable.repo),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(64.dp)
        )
        Spacer(Modifier.height(gapMedium))
        Text(
            text = stringResource(Res.string.search_nothing_found_warning),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(Modifier.height(gapSmall))
        Text(
            text = stringResource(Res.string.search_nothing_found_warning_subtitle),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Normal
        )
    }
}

@Composable
@Preview
private fun SearchScreenPreview(
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
            SearchUiState(
                searchQuery = if (param.isQueryEmpty) "" else "some query",
                listState =  if (param.isLoading)
                    ListUiState.Loading
                else ListUiState.DataShown(
                    repositories = if (param.nothingFound) emptyList() else repositories
                )
            )
        )
    }

    GitHubMaterialTheme(darkTheme = param.isDark) {
        SearchView(
            state = state,
            onSearchQueryChange = {},
            onClearSearch = {},
            onSearchRetry = {},
            onLoadNextPage = {}
        )
    }
}

private data class MainPreviewParameter(
    val isDark: Boolean = false,
    val isLoading: Boolean = false,
    val isQueryEmpty: Boolean = false,
    val nothingFound: Boolean = false
)

private class MainScreenPreviewProvider : PreviewParameterProvider<MainPreviewParameter> {
    override val values = sequenceOf(
        MainPreviewParameter(),
        MainPreviewParameter(isDark = true),
        MainPreviewParameter(isLoading = true),
        MainPreviewParameter(isQueryEmpty = true),
        MainPreviewParameter(nothingFound = true)
    )
}
