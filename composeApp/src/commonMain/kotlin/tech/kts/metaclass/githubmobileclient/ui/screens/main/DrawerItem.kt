package tech.kts.metaclass.githubmobileclient.ui.screens.main

import githubmobileclient.composeapp.generated.resources.profile
import githubmobileclient.composeapp.generated.resources.repo
import githubmobileclient.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.DrawableResource

sealed class DrawerItem(
    val title: String,
    val icon: DrawableResource,
    val destination: DrawerDestination
) {
    object Search : DrawerItem(
        title = "Repositories",
        icon = Res.drawable.repo,
        destination = DrawerDestination.Search
    )

    object Profile : DrawerItem(
        title = "profile",
        icon = Res.drawable.profile,
        destination = DrawerDestination.Profile
    )
}