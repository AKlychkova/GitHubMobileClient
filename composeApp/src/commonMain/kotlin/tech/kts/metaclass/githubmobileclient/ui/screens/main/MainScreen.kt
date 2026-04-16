package tech.kts.metaclass.githubmobileclient.ui.screens.main

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import tech.kts.metaclass.githubmobileclient.ui.screens.profile.ProfileScreen
import tech.kts.metaclass.githubmobileclient.ui.screens.search.SearchScreen
import tech.kts.metaclass.githubmobileclient.ui.theme.drawerIconSize

@Composable
fun MainScreen(
    onNavigateToLogin: () -> Unit,
    modifier: Modifier = Modifier
) {
    val drawerNavController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val drawerItems = listOf(DrawerItem.Search, DrawerItem.Profile)
    val navBackStackEntry by drawerNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                drawerItems.forEach { item ->
                    NavigationDrawerItem(
                        label = { Text(item.title) },
                        icon = {
                            Icon(
                                painter = painterResource(item.icon),
                                contentDescription = null,
                                modifier = Modifier.size(drawerIconSize)
                            )
                        },
                        selected = currentDestination?.hasRoute(item.destination::class) ?: false,
                        onClick = {
                            scope.launch { drawerState.close() }
                            drawerNavController.navigate(item.destination) {
                                popUpTo(DrawerDestination.Search) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        },
        gesturesEnabled = true
    ) {
        NavHost(
            navController = drawerNavController,
            startDestination = DrawerDestination.Search
        ) {
            composable<DrawerDestination.Search> { SearchScreen(modifier) }
            composable<DrawerDestination.Profile> {
                ProfileScreen(
                    onNavigateToLogin,
                    modifier
                )
            }
        }
    }
}
