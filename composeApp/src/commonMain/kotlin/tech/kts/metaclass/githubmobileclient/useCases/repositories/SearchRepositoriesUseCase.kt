package tech.kts.metaclass.githubmobileclient.useCases.repositories

import tech.kts.metaclass.githubmobileclient.entities.GitHubRepository

interface SearchRepositoriesUseCase {
    suspend operator fun invoke(query: String, pageNum: Int): SearchResult<GitHubRepository>
}

class SearchRepositoriesUseCaseImpl(
    private val repository: GitHubRepositoryRepository
): SearchRepositoriesUseCase {
    override suspend fun invoke(query: String, pageNum: Int): SearchResult<GitHubRepository> {
        return repository.searchRepositories(query, pageNum)
    }
}