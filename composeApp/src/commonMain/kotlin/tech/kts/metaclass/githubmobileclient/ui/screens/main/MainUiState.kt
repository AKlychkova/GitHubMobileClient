package tech.kts.metaclass.githubmobileclient.ui.screens.main

import androidx.compose.runtime.Immutable

@Immutable
data class MainUiState(
    val searchQuery: String = "contributed-by:@me",
    val repositories: List<RepositoryUiState> = emptyList(),
    val isLoading: Boolean = false,
    val isLoadingNextPage: Boolean = false,
    val isCachedDataShown: Boolean = false,
    val error: Boolean = false,
    val nextPageNum: Int? = null
)