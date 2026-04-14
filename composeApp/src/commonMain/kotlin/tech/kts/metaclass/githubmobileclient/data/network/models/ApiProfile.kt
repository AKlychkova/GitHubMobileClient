package tech.kts.metaclass.githubmobileclient.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiProfile (
    @SerialName("name")
    val name: String?,
    @SerialName("login")
    val login: String,
    @SerialName("bio")
    val bio: String?,
    @SerialName("location")
    val location: String?,
    @SerialName("followers")
    val followers: Int,
    @SerialName("company")
    val company: String?,
    @SerialName("email")
    val email: String?,
    @SerialName("avatar_url")
    val avatarUrl: String?,
    @SerialName("public_repos")
    val publicRepoCount: Int,
    @SerialName("total_private_repos")
    val privateRepoCount: Int
)