package tech.kts.metaclass.githubmobileclient.ui.screens.splash

import androidx.lifecycle.ViewModel
import tech.kts.metaclass.githubmobileclient.ui.Destination
import tech.kts.metaclass.githubmobileclient.useCases.auth.CheckTokenExistUseCase
import tech.kts.metaclass.githubmobileclient.useCases.preferences.ShouldShowStartScreenUseCase

class SplashViewModel(
    private val shouldShowStartScreen: ShouldShowStartScreenUseCase,
    private val tokenExist: CheckTokenExistUseCase
) : ViewModel() {
    suspend fun getStartDestination(): Destination {
        return if (shouldShowStartScreen()) {
            Destination.Start
        } else if (!tokenExist()) {
            Destination.Login
        } else {
            Destination.Main
        }
    }
}