package tech.kts.metaclass.githubmobileclient.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import tech.kts.metaclass.githubmobileclient.data.repositories.AuthRepository

class LoginViewModel(
   private val repository: AuthRepository
) : ViewModel() {
    private val _state = MutableStateFlow(LoginUiState.Initial)
    val state: StateFlow<LoginUiState> = _state.asStateFlow()

    private val _events = MutableSharedFlow<LoginUiEvent>()
    val events: SharedFlow<LoginUiEvent> = _events.asSharedFlow()

    fun onUsernameChange(value: String) {
        _state.update { current ->
            current.copy(
                username = value,
                isLoginButtonActive = isLoginButtonActive(value, current.password)
            )
        }
    }

    fun onPasswordChange(value: String) {
        _state.update { current ->
            current.copy(
                password = value,
                isLoginButtonActive = isLoginButtonActive(current.username, value)
            )
        }
    }

    fun onPasswordVisibilityClick() {
        _state.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
    }

    fun onLoginClick() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.login().fold(
                onSuccess = {
                    pushEvent(LoginUiEvent.LoginSuccessEvent)
                },
                onFailure = {
                    _state.update { it.copy(error = true) }
                }
            )
        }
    }

    private fun isLoginButtonActive(username: String, password: String): Boolean {
        return username.isNotBlank() && password.isNotBlank()
    }

    private fun pushEvent(event: LoginUiEvent) = viewModelScope.launch {
        _events.emit(event)
    }
}