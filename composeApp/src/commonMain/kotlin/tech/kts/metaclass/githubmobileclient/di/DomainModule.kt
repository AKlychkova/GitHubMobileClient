package tech.kts.metaclass.githubmobileclient.di

import org.koin.dsl.module
import tech.kts.metaclass.githubmobileclient.useCases.auth.CheckTokenExistUseCase
import tech.kts.metaclass.githubmobileclient.useCases.auth.CheckTokenExistUseCaseImpl
import tech.kts.metaclass.githubmobileclient.useCases.auth.LoginUseCase
import tech.kts.metaclass.githubmobileclient.useCases.auth.LoginUseCaseImpl
import tech.kts.metaclass.githubmobileclient.useCases.auth.LogoutUseCase
import tech.kts.metaclass.githubmobileclient.useCases.auth.LogoutUseCaseImpl
import tech.kts.metaclass.githubmobileclient.useCases.onboarding.IsOnboardingCompletedUseCase
import tech.kts.metaclass.githubmobileclient.useCases.onboarding.IsOnboardingCompletedUseCaseImpl
import tech.kts.metaclass.githubmobileclient.useCases.onboarding.CompleteOnboardingUseCase
import tech.kts.metaclass.githubmobileclient.useCases.onboarding.CompleteOnboardingUseCaseImpl
import tech.kts.metaclass.githubmobileclient.useCases.onboarding.GetOnboardingPagesUseCase
import tech.kts.metaclass.githubmobileclient.useCases.onboarding.GetOnboardingPagesUseCaseImpl
import tech.kts.metaclass.githubmobileclient.useCases.profile.GetProfileUseCase
import tech.kts.metaclass.githubmobileclient.useCases.profile.GetProfileUseCaseImpl
import tech.kts.metaclass.githubmobileclient.useCases.repositories.ClearRepositoriesCacheUseCase
import tech.kts.metaclass.githubmobileclient.useCases.repositories.ClearRepositoriesCacheUseCaseImpl
import tech.kts.metaclass.githubmobileclient.useCases.repositories.SearchRepositoriesUseCase
import tech.kts.metaclass.githubmobileclient.useCases.repositories.SearchRepositoriesUseCaseImpl

val domainModule = module {
    single<LoginUseCase> { LoginUseCaseImpl(get()) }
    single<LogoutUseCase> { LogoutUseCaseImpl(get(), get()) }
    single<SearchRepositoriesUseCase> { SearchRepositoriesUseCaseImpl(get()) }
    single<IsOnboardingCompletedUseCase> { IsOnboardingCompletedUseCaseImpl(get()) }
    single<CompleteOnboardingUseCase> { CompleteOnboardingUseCaseImpl(get()) }
    single<GetOnboardingPagesUseCase> { GetOnboardingPagesUseCaseImpl(get()) }
    single<CheckTokenExistUseCase> { CheckTokenExistUseCaseImpl(get()) }
    single<GetProfileUseCase> { GetProfileUseCaseImpl(get()) }
    single<ClearRepositoriesCacheUseCase> { ClearRepositoriesCacheUseCaseImpl(get()) }
}