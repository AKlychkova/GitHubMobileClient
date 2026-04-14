package tech.kts.metaclass.githubmobileclient.ui.screens.main

import githubmobileclient.composeapp.generated.resources.Profile
import githubmobileclient.composeapp.generated.resources.Repo
import githubmobileclient.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.DrawableResource

sealed class DrawerItem(
    val title: String,
    val icon: DrawableResource,
    val destination: DrawerDestination
) {
    object Search : DrawerItem(
        title = "Repositories",
        icon = Res.drawable.Repo,
        destination = DrawerDestination.Search
    )

    object Profile : DrawerItem(
        title = "Profile",
        icon = Res.drawable.Profile,
        destination = DrawerDestination.Profile
    )
}