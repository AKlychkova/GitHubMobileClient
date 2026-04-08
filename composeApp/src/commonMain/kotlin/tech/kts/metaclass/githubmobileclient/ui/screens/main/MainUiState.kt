package tech.kts.metaclass.githubmobileclient.ui.screens.main

import androidx.compose.runtime.Immutable

@Immutable
data class MainUiState(
    val isLoading: Boolean = false,
    val searchQuery: String = "contributed-by:@me",
    val repositories: List<RepositoryUiState> = emptyList(),
    val isCachedDataShown: Boolean = false,
    val error: Boolean = false
)