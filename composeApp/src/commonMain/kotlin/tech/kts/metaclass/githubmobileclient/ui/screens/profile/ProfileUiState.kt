package tech.kts.metaclass.githubmobileclient.ui.screens.profile

data class ProfileUiState(
    val login: String,
    val name: String?,
    val bio: String?,
    val location: String?,
    val followers: String,
    val company: String?,
    val email: String?,
    val avatarUrl: String?,
    val repositoriesCount: String
)
