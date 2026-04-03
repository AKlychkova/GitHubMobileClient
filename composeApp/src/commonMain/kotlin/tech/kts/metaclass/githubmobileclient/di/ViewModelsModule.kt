package tech.kts.metaclass.githubmobileclient.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import tech.kts.metaclass.githubmobileclient.ui.screens.login.LoginViewModel
import tech.kts.metaclass.githubmobileclient.ui.screens.main.MainViewModel
import tech.kts.metaclass.githubmobileclient.ui.screens.splash.SplashViewModel
import tech.kts.metaclass.githubmobileclient.ui.screens.start.StartViewModel

val viewModelsModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { StartViewModel(get()) }
    viewModel { MainViewModel(get()) }
    viewModel { SplashViewModel(get(), get()) }
}