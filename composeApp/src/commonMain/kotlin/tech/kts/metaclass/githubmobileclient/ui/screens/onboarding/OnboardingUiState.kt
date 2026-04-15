package tech.kts.metaclass.githubmobileclient.ui.screens.onboarding

import tech.kts.metaclass.githubmobileclient.entities.OnboardingPage

data class OnboardingUiState(
    val pages: List<OnboardingPage> = emptyList()
)
