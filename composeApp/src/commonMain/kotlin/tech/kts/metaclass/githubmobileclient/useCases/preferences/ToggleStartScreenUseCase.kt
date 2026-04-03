package tech.kts.metaclass.githubmobileclient.useCases.preferences

interface ToggleStartScreenUseCase {
    suspend operator fun invoke(value: Boolean)
}

class ToggleStartScreenUseCaseImpl(
    private val repository: PreferencesRepository
): ToggleStartScreenUseCase {
    override suspend fun invoke(value: Boolean) {
        repository.toggleStartScreen(value)
    }
}