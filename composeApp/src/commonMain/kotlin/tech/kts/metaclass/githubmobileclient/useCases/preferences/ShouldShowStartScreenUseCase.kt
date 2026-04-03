package tech.kts.metaclass.githubmobileclient.useCases.preferences

interface ShouldShowStartScreenUseCase {
    suspend operator fun invoke(): Boolean
}

class ShouldShowStartScreenUseCaseImpl(
    private val repository: PreferencesRepository
): ShouldShowStartScreenUseCase {
    override suspend fun invoke(): Boolean = repository.shouldShowStartScreen()
}