package tech.kts.metaclass.githubmobileclient.ui.screens.main

import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import tech.kts.metaclass.githubmobileclient.entities.GitHubRepository
import tech.kts.metaclass.githubmobileclient.ui.utils.NumberFormatter

class UiRepositoryMapper(
    private val formatter: NumberFormatter
) {
    fun toUiState(repository: GitHubRepository): RepositoryUiState {
        return RepositoryUiState(
            id = repository.id,
            fullName = repository.owner.username + "/" + repository.name,
            description = repository.description,
            visibility = repository.visibility.capitalize(Locale.current),
            language = repository.language,
            stars = formatter.format(repository.stars),
            forks = formatter.format(repository.forks),
            openIssues = formatter.format(repository.openIssues),
            avatarUrl = repository.owner.avatarUrl
        )
    }
}