package tech.kts.metaclass.githubmobileclient.entities

data class GitHubRepository (
    val name: String,
    val description: String,
    val language: ProgrammingLanguage,
    val stars: Int,
    val owner: Owner
)