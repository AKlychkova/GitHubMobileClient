package tech.kts.metaclass.githubmobileclient.ui

import kotlinx.serialization.Serializable

@Serializable
sealed interface Destination {
    @Serializable
    object Start

    @Serializable
    object Login

    @Serializable
    data object Main : Destination
}