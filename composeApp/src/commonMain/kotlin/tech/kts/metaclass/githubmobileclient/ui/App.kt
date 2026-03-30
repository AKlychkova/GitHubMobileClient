package tech.kts.metaclass.githubmobileclient.ui

import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import tech.kts.metaclass.githubmobileclient.ui.screens.login.LoginScreen
import tech.kts.metaclass.githubmobileclient.ui.screens.main.MainScreen
import tech.kts.metaclass.githubmobileclient.ui.screens.splash.SplashScreen
import tech.kts.metaclass.githubmobileclient.ui.screens.start.StartScreen
import tech.kts.metaclass.githubmobileclient.ui.theme.GitHubMaterialTheme

@Composable
fun App() {
    LaunchedEffect(Unit) {
        Napier.base(DebugAntilog())
    }

    GitHubMaterialTheme {
        RootNavHost()
    }
}

@Composable
private fun RootNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Destination.Splash,
    ) {
        composable<Destination.Splash> {
            SplashScreen (
                onNavigate = { destination ->
                    navController.navigate(route = destination) {
                        popUpTo("splash") { inclusive = true }
                    }
                }
            )
        }
        composable<Destination.Start> {
            StartScreen(
                onNavigateToLogin = {
                    navController.navigate(route = Destination.Login) {
                        popUpTo<Destination.Start> {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable<Destination.Login> {
            LoginScreen(
                onNavigateToMain = {
                    navController.navigate(route = Destination.Main) {
                        popUpTo<Destination.Login> {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable<Destination.Main> {
            MainScreen()
        }
    }
}