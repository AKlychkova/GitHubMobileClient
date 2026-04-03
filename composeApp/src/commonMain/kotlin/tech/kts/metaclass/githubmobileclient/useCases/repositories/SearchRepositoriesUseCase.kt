package tech.kts.metaclass.githubmobileclient.useCases.repositories

interface SearchRepositoriesUseCase {
    suspend operator fun invoke(query: String): SearchRepositoriesResult
}

class SearchRepositoriesUseCaseImpl(
    private val repository: GitHubRepositoryRepository
): SearchRepositoriesUseCase {
    override suspend fun invoke(query: String): SearchRepositoriesResult {
        return repository.searchRepositories(query)
    }
}