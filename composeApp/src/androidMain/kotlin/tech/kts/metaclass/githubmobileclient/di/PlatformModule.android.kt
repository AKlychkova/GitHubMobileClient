package tech.kts.metaclass.githubmobileclient.di

import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.plugin.module.dsl.single
import tech.kts.metaclass.githubmobileclient.platform.AndroidDirectoriesProvider
import tech.kts.metaclass.githubmobileclient.platform.DirectoriesProvider

actual val platformModule = module {
    single<AndroidDirectoriesProvider>() bind DirectoriesProvider::class
}