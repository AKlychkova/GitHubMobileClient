package tech.kts.metaclass.githubmobileclient.useCases.repositories

interface GitHubRepositoryRepository {
    suspend fun searchRepositories(query: String): SearchRepositoriesResult
}