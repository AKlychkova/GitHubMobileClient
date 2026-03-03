package tech.kts.metaclass.githubmobileclient.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import tech.kts.metaclass.githubmobileclient.ui.login.LoginScreen
import tech.kts.metaclass.githubmobileclient.ui.main.MainScreen
import tech.kts.metaclass.githubmobileclient.ui.start.StartScreen

@Composable
fun App() {
    MaterialTheme {
        RootNavHost()
    }
}

@Composable
private fun RootNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Destination.Start,
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.safeDrawing.asPaddingValues())
    ) {
        composable<Destination.Start> {
            StartScreen(
                onNavigateToLogin = {
                    navController.navigate(route = Destination.Login)
                }
            )

        }
        composable<Destination.Login> {
            LoginScreen(
                onNavigateToMain = {
                    navController.navigate(route = Destination.Main) {
                        popUpTo<Destination.Start> {
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