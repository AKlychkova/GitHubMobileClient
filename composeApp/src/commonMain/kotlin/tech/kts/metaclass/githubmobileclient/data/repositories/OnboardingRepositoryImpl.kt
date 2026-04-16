package tech.kts.metaclass.githubmobileclient.data.repositories

import githubmobileclient.composeapp.generated.resources.profile_octocat
import githubmobileclient.composeapp.generated.resources.Res
import githubmobileclient.composeapp.generated.resources.search_repo_octocat
import githubmobileclient.composeapp.generated.resources.onboarding_desc_1
import githubmobileclient.composeapp.generated.resources.onboarding_desc_2
import githubmobileclient.composeapp.generated.resources.onboarding_title_1
import githubmobileclient.composeapp.generated.resources.onboarding_title_2
import tech.kts.metaclass.githubmobileclient.data.storage.OnboardingStorage
import tech.kts.metaclass.githubmobileclient.entities.OnboardingPage
import tech.kts.metaclass.githubmobileclient.useCases.onboarding.OnboardingRepository

class OnboardingRepositoryImpl(
    private val onboardingStorage: OnboardingStorage
) : OnboardingRepository {

    override fun getOnboardingPages(): List<OnboardingPage> = listOf(
        OnboardingPage(
            image = Res.drawable.search_repo_octocat,
            title = Res.string.onboarding_title_1,
            description = Res.string.onboarding_desc_1
        ),
        OnboardingPage(
            image = Res.drawable.profile_octocat,
            title = Res.string.onboarding_title_2,
            description = Res.string.onboarding_desc_2
        )
    )

    override suspend fun isOnboardingCompleted(): Boolean =
        onboardingStorage.isOnboardingCompleted()

    override suspend fun completeOnboarding() {
        onboardingStorage.setOnboardingCompleted()
    }
}