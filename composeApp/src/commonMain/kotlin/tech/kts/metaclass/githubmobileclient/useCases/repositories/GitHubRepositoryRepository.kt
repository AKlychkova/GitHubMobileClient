package tech.kts.metaclass.githubmobileclient.useCases.repositories

import tech.kts.metaclass.githubmobileclient.entities.GitHubRepository

interface GitHubRepositoryRepository {
    suspend fun searchRepositories(
        query: String,
        pageNum: Int
    ): SearchResult<GitHubRepository>
}