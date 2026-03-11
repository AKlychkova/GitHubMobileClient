package tech.kts.metaclass.githubmobileclient.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiOwner (
    @SerialName("id")
    val id: Long,
    @SerialName("login")
    val username: String,
    @SerialName("avatar_url")
    val avatarUrl: String
)