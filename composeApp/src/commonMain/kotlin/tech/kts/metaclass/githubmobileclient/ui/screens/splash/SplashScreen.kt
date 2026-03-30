package tech.kts.metaclass.githubmobileclient.ui.screens.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import tech.kts.metaclass.githubmobileclient.ui.Destination

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = viewModel { SplashViewModel() },
    onNavigate: (Destination) -> Unit
) {
    LaunchedEffect(Unit) {
        val destination = viewModel.getStartDestination()
        onNavigate(destination)
    }
}