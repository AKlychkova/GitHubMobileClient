package tech.kts.metaclass.githubmobileclient.ui.screens.onboarding

sealed interface OnboardingUiEvent {
    data object NavigateToLogin: OnboardingUiEvent
}