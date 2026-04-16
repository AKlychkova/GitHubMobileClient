package tech.kts.metaclass.githubmobileclient.data.network.models

import kotlinx.serialization.Serializable

@Serializable
data class ItemsWrapper<T> (
    val items: List<T>
)