package tech.kts.metaclass.githubmobileclient.di

import org.koin.dsl.module
import tech.kts.metaclass.githubmobileclient.platform.DirectoriesProvider
import tech.kts.metaclass.githubmobileclient.platform.IosDirectoriesProvider
import tech.kts.metaclass.githubmobileclient.platform.iosDatabaseBuilder

actual val platformModule = module {
    single<DirectoriesProvider> { IosDirectoriesProvider() }
    //single<IosAuthLauncher>() bind AuthLauncher::class
    single { iosDatabaseBuilder() }
}