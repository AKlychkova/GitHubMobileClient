package tech.kts.metaclass.githubmobileclient.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiGitHubRepository(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("description")
    val description: String?,
    @SerialName("visibility")
    val visibility: String,
    @SerialName("language")
    val language: String?,
    @SerialName("stargazers_count")
    val stars: Int,
    @SerialName("forks_count")
    val forks: Int,
    @SerialName("open_issues_count")
    val openIssues: Int,
    @SerialName("owner")
    val owner: ApiUser
)