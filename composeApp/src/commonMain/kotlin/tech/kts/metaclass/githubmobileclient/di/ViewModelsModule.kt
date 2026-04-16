package tech.kts.metaclass.githubmobileclient.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import tech.kts.metaclass.githubmobileclient.ui.screens.login.LoginViewModel
import tech.kts.metaclass.githubmobileclient.ui.screens.profile.ProfileViewModel
import tech.kts.metaclass.githubmobileclient.ui.screens.profile.UiProfileMapper
import tech.kts.metaclass.githubmobileclient.ui.screens.search.SearchViewModel
import tech.kts.metaclass.githubmobileclient.ui.screens.search.UiRepositoryMapper
import tech.kts.metaclass.githubmobileclient.ui.screens.splash.SplashViewModel
import tech.kts.metaclass.githubmobileclient.ui.screens.onboarding.OnboardingViewModel
import tech.kts.metaclass.githubmobileclient.ui.utils.NumberFormatter

val viewModelsModule = module {
    single<NumberFormatter> { NumberFormatter() }
    factory<UiRepositoryMapper> { UiRepositoryMapper(get()) }
    factory<UiProfileMapper> { UiProfileMapper(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { OnboardingViewModel(get(), get()) }
    viewModel { SearchViewModel(get(), get()) }
    viewModel { SplashViewModel(get(), get()) }
    viewModel { ProfileViewModel(get(), get(), get()) }
}