package tech.kts.metaclass.githubmobileclient.di

import org.koin.dsl.module
import org.koin.dsl.onClose
import tech.kts.metaclass.githubmobileclient.data.network.auth.GitHubAuthConfig
import tech.kts.metaclass.githubmobileclient.platform.AuthRepository
import tech.kts.metaclass.githubmobileclient.platform.AndroidAuthRepository
import tech.kts.metaclass.githubmobileclient.platform.AndroidDirectoriesProvider
import tech.kts.metaclass.githubmobileclient.platform.DirectoriesProvider
import tech.kts.metaclass.githubmobileclient.platform.androidDatabaseBuilder

actual val platformModule = module {
    single<DirectoriesProvider> { AndroidDirectoriesProvider(get()) }
    single { androidDatabaseBuilder(get()) }
    single {
        AndroidAuthRepository(
            tokenRepository = get(),
            context = get(),
            config = GitHubAuthConfig
        )
    } onClose { it?.dispose() }

    single<AuthRepository> { get<AndroidAuthRepository>() }
}