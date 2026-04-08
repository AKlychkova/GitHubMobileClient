package tech.kts.metaclass.githubmobileclient.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tech.kts.metaclass.githubmobileclient.useCases.auth.LoginUseCase

class LoginViewModel(
   private val login: LoginUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(LoginUiState())
    val state: StateFlow<LoginUiState> = _state.asStateFlow()

    private val _events = MutableSharedFlow<LoginUiEvent>()
    val events: SharedFlow<LoginUiEvent> = _events.asSharedFlow()

    fun onLoginClick() {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch(Dispatchers.IO) {
            login().fold(
                onSuccess = {
                    pushEvent(LoginUiEvent.LoginSuccessEvent)
                },
                onFailure = { e ->
                    Napier.e("Auth error", e, tag = "Network")
                    pushEvent(LoginUiEvent.LoginFailureEvent)
                    _state.update { it.copy(isLoading = false) }
                }
            )
        }
    }
    private fun pushEvent(event: LoginUiEvent) = viewModelScope.launch {
        _events.emit(event)
    }
}