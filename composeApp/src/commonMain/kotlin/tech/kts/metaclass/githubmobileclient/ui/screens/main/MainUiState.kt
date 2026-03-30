package tech.kts.metaclass.githubmobileclient.ui.screens.main

import androidx.compose.runtime.Immutable
import tech.kts.metaclass.githubmobileclient.entities.GitHubRepository

@Immutable
data class MainUiState(
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val repositories: List<GitHubRepository> = emptyList(),
    val error: String? = null
)