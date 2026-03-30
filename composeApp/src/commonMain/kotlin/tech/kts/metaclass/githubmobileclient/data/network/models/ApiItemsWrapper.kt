package tech.kts.metaclass.githubmobileclient.data.network.models

import kotlinx.serialization.Serializable

@Serializable
data class ApiItemsWrapper<T> (
    val items: List<T>
)