package tech.kts.metaclass.githubmobileclient.di

import org.koin.dsl.module
import tech.kts.metaclass.githubmobileclient.useCases.auth.CheckTokenExistUseCase
import tech.kts.metaclass.githubmobileclient.useCases.auth.CheckTokenExistUseCaseImpl
import tech.kts.metaclass.githubmobileclient.useCases.auth.LoginUseCase
import tech.kts.metaclass.githubmobileclient.useCases.auth.LoginUseCaseImpl
import tech.kts.metaclass.githubmobileclient.useCases.auth.LogoutUseCase
import tech.kts.metaclass.githubmobileclient.useCases.auth.LogoutUseCaseImpl
import tech.kts.metaclass.githubmobileclient.useCases.preferences.ShouldShowStartScreenUseCase
import tech.kts.metaclass.githubmobileclient.useCases.preferences.ShouldShowStartScreenUseCaseImpl
import tech.kts.metaclass.githubmobileclient.useCases.preferences.ToggleStartScreenUseCase
import tech.kts.metaclass.githubmobileclient.useCases.preferences.ToggleStartScreenUseCaseImpl
import tech.kts.metaclass.githubmobileclient.useCases.repositories.SearchRepositoriesUseCase
import tech.kts.metaclass.githubmobileclient.useCases.repositories.SearchRepositoriesUseCaseImpl

val domainModule = module {
    single<LoginUseCase> { LoginUseCaseImpl(get()) }
    single<LogoutUseCase> { LogoutUseCaseImpl(get()) }
    single<SearchRepositoriesUseCase> { SearchRepositoriesUseCaseImpl(get()) }
    single<ShouldShowStartScreenUseCase> { ShouldShowStartScreenUseCaseImpl(get()) }
    single<ToggleStartScreenUseCase> { ToggleStartScreenUseCaseImpl(get()) }
    single<CheckTokenExistUseCase> { CheckTokenExistUseCaseImpl(get()) }
}