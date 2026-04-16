package tech.kts.metaclass.githubmobileclient.ui.screens.splash

import androidx.lifecycle.ViewModel
import tech.kts.metaclass.githubmobileclient.ui.Destination
import tech.kts.metaclass.githubmobileclient.useCases.auth.CheckTokenExistUseCase
import tech.kts.metaclass.githubmobileclient.useCases.onboarding.IsOnboardingCompletedUseCase

class SplashViewModel(
    private val isOnboardingCompleted: IsOnboardingCompletedUseCase,
    private val tokenExist: CheckTokenExistUseCase
) : ViewModel() {
    suspend fun getStartDestination(): Destination {
        return if (!isOnboardingCompleted()) {
            Destination.Start
        } else if (!tokenExist()) {
            Destination.Login
        } else {
            Destination.Main
        }
    }
}