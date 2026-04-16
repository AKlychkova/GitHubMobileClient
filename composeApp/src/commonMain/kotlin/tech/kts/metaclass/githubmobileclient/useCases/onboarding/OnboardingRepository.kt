package tech.kts.metaclass.githubmobileclient.useCases.onboarding

import tech.kts.metaclass.githubmobileclient.entities.OnboardingPage

interface OnboardingRepository {
    fun getOnboardingPages(): List<OnboardingPage>
    suspend fun isOnboardingCompleted(): Boolean
    suspend fun completeOnboarding()
}