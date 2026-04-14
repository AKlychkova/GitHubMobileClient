package tech.kts.metaclass.githubmobileclient.ui.screens.profile

import tech.kts.metaclass.githubmobileclient.entities.Profile
import tech.kts.metaclass.githubmobileclient.ui.utils.NumberFormatter

class UiProfileMapper(
    private val formatter: NumberFormatter
) {
    fun toUiState(profile: Profile): ProfileUiState {
        return ProfileUiState(
            login = profile.login,
            name = profile.name,
            bio = profile.bio,
            location = profile.location,
            followers = formatter.format(profile.followers),
            company = profile.company,
            email = profile.email?.toString(),
            avatarUrl = profile.avatarUrl,
            repositoriesCount = formatter.format(profile.repositoriesCount)
        )
    }
}