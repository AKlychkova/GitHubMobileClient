package tech.kts.metaclass.githubmobileclient.ui.screens.main

import kotlinx.serialization.Serializable

@Serializable
sealed interface DrawerDestination {
    @Serializable
    object Search: DrawerDestination

    @Serializable
    object Profile: DrawerDestination
}