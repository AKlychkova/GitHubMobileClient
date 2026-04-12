package tech.kts.metaclass.githubmobileclient.ui.screens.main

import androidx.compose.runtime.Immutable

@Immutable
data class MainUiState(
    val searchQuery: String = "contributed-by:@me",
    val listState: ListUiState = ListUiState.DataShown()
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