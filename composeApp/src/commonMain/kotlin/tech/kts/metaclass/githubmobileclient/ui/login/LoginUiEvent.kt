package tech.kts.metaclass.githubmobileclient.ui.login

sealed interface LoginUiEvent {
    data object LoginSuccessEvent: LoginUiEvent
}