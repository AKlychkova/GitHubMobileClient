package tech.kts.metaclass.githubmobileclient.ui.screens.splash

import androidx.lifecycle.ViewModel
import tech.kts.metaclass.githubmobileclient.data.repositories.PreferencesRepository
import tech.kts.metaclass.githubmobileclient.data.repositories.TokenRepository
import tech.kts.metaclass.githubmobileclient.ui.Destination

class SplashViewModel(
    private val preferencesRepository: PreferencesRepository,
    private val tokenRepository: TokenRepository
) : ViewModel() {
    suspend fun getStartDestination(): Destination {
        return if (preferencesRepository.shouldShowStartScreen()) {
            Destination.Start
        } else if (tokenRepository.getToken() == null) {
            Destination.Login
        } else {
            Destination.Main
        }
    }
}