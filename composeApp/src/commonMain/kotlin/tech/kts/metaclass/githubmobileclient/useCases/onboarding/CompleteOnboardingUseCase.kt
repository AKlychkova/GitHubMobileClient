package tech.kts.metaclass.githubmobileclient.useCases.onboarding

interface CompleteOnboardingUseCase {
    suspend operator fun invoke()
}

class CompleteOnboardingUseCaseImpl(
    private val repository: OnboardingRepository
): CompleteOnboardingUseCase {
    override suspend fun invoke() {
        repository.completeOnboarding()
    }
}