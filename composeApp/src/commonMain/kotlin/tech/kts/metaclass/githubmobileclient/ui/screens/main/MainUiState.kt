package tech.kts.metaclass.githubmobileclient.ui.screens.main

import tech.kts.metaclass.githubmobileclient.entities.GitHubRepository

data class MainUiState(
    val filteredItems: List<GitHubRepository> = emptyList()
)