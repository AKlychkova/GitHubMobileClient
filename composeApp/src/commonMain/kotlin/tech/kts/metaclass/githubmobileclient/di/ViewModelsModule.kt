package tech.kts.metaclass.githubmobileclient.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import tech.kts.metaclass.githubmobileclient.ui.screens.login.LoginViewModel
import tech.kts.metaclass.githubmobileclient.ui.screens.main.MainViewModel
import tech.kts.metaclass.githubmobileclient.ui.screens.main.UiRepositoryMapper
import tech.kts.metaclass.githubmobileclient.ui.screens.splash.SplashViewModel
import tech.kts.metaclass.githubmobileclient.ui.screens.start.StartViewModel
import tech.kts.metaclass.githubmobileclient.ui.utils.NumberFormatter

val viewModelsModule = module {
    single<NumberFormatter> { NumberFormatter() }
    factory<UiRepositoryMapper> { UiRepositoryMapper(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { StartViewModel(get()) }
    viewModel { MainViewModel(get(), get()) }
    viewModel { SplashViewModel(get(), get()) }
}