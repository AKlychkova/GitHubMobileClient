package tech.kts.metaclass.githubmobileclient.ui.screens.profile

sealed interface ProfileScreenUiState {
    data object Loading : ProfileScreenUiState
    data class Success(val profile: ProfileUiState) : ProfileScreenUiState
    data object Error : ProfileScreenUiState
}