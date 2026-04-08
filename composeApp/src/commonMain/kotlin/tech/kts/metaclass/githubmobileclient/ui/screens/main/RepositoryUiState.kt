package tech.kts.metaclass.githubmobileclient.ui.screens.main

import tech.kts.metaclass.githubmobileclient.entities.ProgrammingLanguage

data class RepositoryUiState (
    val id: Int,
    val fullName: String,
    val description: String?,
    val visibility: String,
    val language: ProgrammingLanguage,
    val stars: String,
    val forks: String,
    val openIssues: String,
    val avatarUrl: String?
)