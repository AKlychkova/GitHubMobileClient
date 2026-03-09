package tech.kts.metaclass.githubmobileclient.ui.screens.main

import androidx.compose.runtime.Immutable
import tech.kts.metaclass.githubmobileclient.entities.GitHubRepository

@Immutable
data class MainUiState(
    val filteredItems: List<GitHubRepository> = emptyList()
)