package tech.kts.metaclass.githubmobileclient.di

import org.koin.dsl.module
import tech.kts.metaclass.githubmobileclient.data.repositories.GitHubRepositoryRepositoryImpl
import tech.kts.metaclass.githubmobileclient.data.repositories.OnboardingRepositoryImpl
import tech.kts.metaclass.githubmobileclient.data.repositories.ProfileRepositoryImpl
import tech.kts.metaclass.githubmobileclient.data.repositories.TokenRepositoryImpl
import tech.kts.metaclass.githubmobileclient.useCases.auth.TokenRepository
import tech.kts.metaclass.githubmobileclient.useCases.onboarding.OnboardingRepository
import tech.kts.metaclass.githubmobileclient.useCases.profile.ProfileRepository
import tech.kts.metaclass.githubmobileclient.useCases.repositories.GitHubRepositoryRepository

val dataModule = module {
    includes(
        dataStoreModule,
        networkModule,
        dataBaseModule,
    )
    single<OnboardingRepository> { OnboardingRepositoryImpl(get()) }
    single<TokenRepository> { TokenRepositoryImpl(get()) }
    single<GitHubRepositoryRepository> {
        GitHubRepositoryRepositoryImpl(
            api = get(),
            apiMapper = get(),
            dbMapper = get(),
            userDao = get(),
            repositoryDao = get()
        )
    }
    single<ProfileRepository> { ProfileRepositoryImpl(get(), get()) }
}