package tech.kts.metaclass.githubmobileclient.useCases.onboarding

import tech.kts.metaclass.githubmobileclient.entities.OnboardingPage

interface GetOnboardingPagesUseCase {
    operator fun invoke(): List<OnboardingPage>
}

class GetOnboardingPagesUseCaseImpl(
    private val repository: OnboardingRepository,
) : GetOnboardingPagesUseCase {
    override operator fun invoke(): List<OnboardingPage> = repository.getOnboardingPages()
}