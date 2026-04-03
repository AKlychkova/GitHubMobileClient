package tech.kts.metaclass.githubmobileclient.useCases.preferences

interface PreferencesRepository {
    suspend fun shouldShowStartScreen(): Boolean
    suspend fun toggleStartScreen(isShown: Boolean): Result<Unit>
}