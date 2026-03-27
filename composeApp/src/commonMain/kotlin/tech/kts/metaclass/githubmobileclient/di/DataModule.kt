package tech.kts.metaclass.githubmobileclient.di

import org.koin.core.qualifier.named
import org.koin.dsl.module
import tech.kts.metaclass.githubmobileclient.data.repositories.AuthRepository
import tech.kts.metaclass.githubmobileclient.data.repositories.AuthRepositoryImpl
import tech.kts.metaclass.githubmobileclient.data.network.auth.GitHubAuthConfig
import tech.kts.metaclass.githubmobileclient.data.repositories.GitHubRepositoryRepository
import tech.kts.metaclass.githubmobileclient.data.repositories.GitHubRepositoryRepositoryImpl
import tech.kts.metaclass.githubmobileclient.data.repositories.PreferencesRepository
import tech.kts.metaclass.githubmobileclient.data.repositories.PreferencesRepositoryImpl
import tech.kts.metaclass.githubmobileclient.data.repositories.TokenRepository
import tech.kts.metaclass.githubmobileclient.data.repositories.TokenRepositoryImpl
import tech.kts.metaclass.githubmobileclient.platform.getAuthLauncher

val dataModule = module {
    includes(
        dataStoreModule,
        networkModule,
        dataBaseModule,
    )
    single<PreferencesRepository> { PreferencesRepositoryImpl(get()) }
    single<TokenRepository> { TokenRepositoryImpl(get()) }
    single<GitHubRepositoryRepository> {
        GitHubRepositoryRepositoryImpl(
            api = get(),
            apiMapper = get(),
            dbMapper = get(),
            userDao = get(),
            repositoryDao = get()
        )
    }
    single<AuthRepository> {
        AuthRepositoryImpl(
            config = GitHubAuthConfig,
            launcher = getAuthLauncher(),
            httpClient = get(named("github")),
            tokenRepository = get()
        )
    }
}