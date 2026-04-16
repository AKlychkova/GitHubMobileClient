package tech.kts.metaclass.githubmobileclient.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import tech.kts.metaclass.githubmobileclient.useCases.auth.LogoutUseCase
import tech.kts.metaclass.githubmobileclient.useCases.profile.GetProfileUseCase

class ProfileViewModel(
    private val getProfile: GetProfileUseCase,
    private val logout: LogoutUseCase,
    private val mapper: UiProfileMapper
) : ViewModel() {
    private val _state = MutableStateFlow< ProfileScreenUiState>(ProfileScreenUiState.Loading)
    val state: StateFlow<ProfileScreenUiState> = _state.asStateFlow()

    init {
        loadProfile()
    }

    fun onRetry() {
        loadProfile()
    }

    fun onLogout() {
        viewModelScope.launch {
            logout()
        }
    }

    private fun loadProfile() {
        viewModelScope.launch {
            _state.value = ProfileScreenUiState.Loading
            getProfile().fold(
                onSuccess = { profile ->
                    _state.value = ProfileScreenUiState.Success(mapper.toUiState(profile))
                },
                onFailure = {
                    _state.value = ProfileScreenUiState.Error
                }
            )
        }
    }
}