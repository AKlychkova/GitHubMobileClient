package tech.kts.metaclass.githubmobileclient.ui

import kotlinx.serialization.Serializable

@Serializable
sealed interface Destination {
    @Serializable
    object Start : Destination

    @Serializable
    object Login : Destination

    @Serializable
    data object Main : Destination
}