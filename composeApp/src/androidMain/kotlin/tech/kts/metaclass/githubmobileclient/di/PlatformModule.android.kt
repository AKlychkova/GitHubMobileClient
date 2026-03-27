package tech.kts.metaclass.githubmobileclient.di

import org.koin.dsl.module
import tech.kts.metaclass.githubmobileclient.platform.AndroidDirectoriesProvider
import tech.kts.metaclass.githubmobileclient.platform.DirectoriesProvider
import tech.kts.metaclass.githubmobileclient.platform.androidDatabaseBuilder

actual val platformModule = module {
    single<DirectoriesProvider> { AndroidDirectoriesProvider(get()) }
    //single<AndroidAuthLauncher>() bind AuthLauncher::class
    single { androidDatabaseBuilder(get()) }
}