package tech.kts.metaclass.githubmobileclient.useCases.onboarding

interface IsOnboardingCompletedUseCase {
    suspend operator fun invoke(): Boolean
}

class IsOnboardingCompletedUseCaseImpl(
    private val repository: OnboardingRepository
): IsOnboardingCompletedUseCase {
    override suspend fun invoke(): Boolean = repository.isOnboardingCompleted()
}