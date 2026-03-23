package tech.kts.metaclass.githubmobileclient.di

import org.koin.dsl.module

val sharedModule = module {
    includes(dataModule, domainModule, viewModelsModule)
}