package tech.kts.metaclass.githubmobileclient.ui.screens.login

sealed interface LoginUiEvent {
    data object LoginSuccessEvent: LoginUiEvent
}