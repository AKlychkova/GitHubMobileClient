package tech.kts.metaclass.githubmobileclient.di

import org.koin.dsl.module
import org.koin.plugin.module.dsl.viewModel
import tech.kts.metaclass.githubmobileclient.ui.screens.login.LoginViewModel
import tech.kts.metaclass.githubmobileclient.ui.screens.main.MainViewModel
import tech.kts.metaclass.githubmobileclient.ui.screens.splash.SplashViewModel
import tech.kts.metaclass.githubmobileclient.ui.screens.start.StartViewModel

val viewModelsModule = module {
    viewModel<LoginViewModel>()
    viewModel<StartViewModel>()
    viewModel<MainViewModel>()
    viewModel<SplashViewModel>()
}