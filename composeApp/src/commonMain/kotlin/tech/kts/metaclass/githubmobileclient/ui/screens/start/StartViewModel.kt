package tech.kts.metaclass.githubmobileclient.ui.screens.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import tech.kts.metaclass.githubmobileclient.useCases.preferences.ToggleStartScreenUseCase

class StartViewModel(
    private val toggleStartScreen: ToggleStartScreenUseCase
): ViewModel() {
    fun disableStartScreen() {
        viewModelScope.launch(Dispatchers.IO) {
            toggleStartScreen(false)
        }
    }
}