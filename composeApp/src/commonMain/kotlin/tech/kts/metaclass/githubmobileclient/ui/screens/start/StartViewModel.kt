package tech.kts.metaclass.githubmobileclient.ui.screens.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import tech.kts.metaclass.githubmobileclient.data.repositories.PreferencesRepository

class StartViewModel(
    private val preferencesRepository: PreferencesRepository
): ViewModel() {
    fun disableStartScreen() {
        viewModelScope.launch(Dispatchers.IO) {
            preferencesRepository.toggleStartScreen(false)
        }
    }
}