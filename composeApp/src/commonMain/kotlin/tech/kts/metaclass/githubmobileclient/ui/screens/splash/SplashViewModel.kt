package tech.kts.metaclass.githubmobileclient.ui.screens.splash

import androidx.lifecycle.ViewModel
import tech.kts.metaclass.githubmobileclient.data.repositories.PreferencesStorage
import tech.kts.metaclass.githubmobileclient.data.repositories.TokenStorage
import tech.kts.metaclass.githubmobileclient.ui.Destination

class SplashViewModel : ViewModel() {
    suspend fun getStartDestination(): Destination {
        return if (PreferencesStorage.shouldShowStartScreen()) {
            Destination.Start
        } else if (TokenStorage.get() == null) {
            Destination.Login
        } else {
            Destination.Main
        }
    }
}