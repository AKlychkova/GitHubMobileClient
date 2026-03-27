package tech.kts.metaclass.githubmobileclient.data.network.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthToken(
    @SerialName("access_token")
    val accessToken: String,
)