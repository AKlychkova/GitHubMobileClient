package tech.kts.metaclass.githubmobileclient.entities

data class Profile(
    val login: String,
    val name: String?,
    val bio: String?,
    val location: String?,
    val followers: Int,
    val company: String?,
    val email: Email?,
    val avatarUrl: String?,
    val repositoriesCount: Int
)
