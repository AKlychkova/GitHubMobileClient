package tech.kts.metaclass.githubmobileclient.useCases.repositories

interface ClearRepositoriesCacheUseCase {
    suspend operator fun invoke()
}

class ClearRepositoriesCacheUseCaseImpl(
    private val repository: GitHubRepositoryRepository
): ClearRepositoriesCacheUseCase {
    override suspend fun invoke() {
        repository.clearCache()
    }
}