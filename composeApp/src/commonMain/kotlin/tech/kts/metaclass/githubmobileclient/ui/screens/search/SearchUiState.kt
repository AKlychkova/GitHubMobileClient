package tech.kts.metaclass.githubmobileclient.ui.screens.search

import androidx.compose.runtime.Immutable

@Immutable
data class SearchUiState(
    val searchQuery: String = "contributed-by:@me",
    val listState: ListUiState = ListUiState.Loading
)

sealed interface ListUiState {
    data object Loading: ListUiState
    data object Error: ListUiState

    @Immutable
    data class DataShown(
        val repositories: List<RepositoryUiState> = emptyList(),
        val isCachedDataShown: Boolean = false,
        val isLoadingNextPage: Boolean = false
    ): ListUiState
}