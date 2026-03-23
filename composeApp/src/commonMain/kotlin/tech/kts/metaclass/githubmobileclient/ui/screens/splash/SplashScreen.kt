package tech.kts.metaclass.githubmobileclient.ui.screens.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import org.koin.compose.viewmodel.koinViewModel
import tech.kts.metaclass.githubmobileclient.ui.Destination

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    onNavigate: (Destination) -> Unit
) {
    val viewModel = koinViewModel<SplashViewModel>()

    LaunchedEffect(Unit) {
        val destination = viewModel.getStartDestination()
        onNavigate(destination)
    }

    SplashView(modifier)
}

@Composable
private fun SplashView(
    modifier: Modifier = Modifier
) {
    // TODO: Here will be shimmer or loader
}