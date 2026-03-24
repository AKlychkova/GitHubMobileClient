package tech.kts.metaclass.githubmobileclient.di

import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.plugin.module.dsl.single
import tech.kts.metaclass.githubmobileclient.platform.DirectoriesProvider
import tech.kts.metaclass.githubmobileclient.platform.IosDirectoriesProvider

actual val platformModule = module {
    single<IosDirectoriesProvider>() bind DirectoriesProvider::class
}