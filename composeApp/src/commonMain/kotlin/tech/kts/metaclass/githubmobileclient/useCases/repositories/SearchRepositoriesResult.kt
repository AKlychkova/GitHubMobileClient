package tech.kts.metaclass.githubmobileclient.useCases.repositories

import tech.kts.metaclass.githubmobileclient.entities.GitHubRepository

sealed interface SearchRepositoriesResult {
    data class Success(
        val repositories: List<GitHubRepository>
    ) : SearchRepositoriesResult

    data class Cached(
        val repositories: List<GitHubRepository>,
        val cause: Throwable
    ) : SearchRepositoriesResult

    data class Failure(
        val cause: Throwable
    ) : SearchRepositoriesResult
}