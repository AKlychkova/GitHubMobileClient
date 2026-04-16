package tech.kts.metaclass.githubmobileclient.entities

data class GitHubRepository (
    val id: Int,
    val name: String,
    val description: String?,
    val visibility: String,
    val language: ProgrammingLanguage,
    val stars: Int,
    val forks: Int,
    val openIssues: Int,
    val owner: User
)