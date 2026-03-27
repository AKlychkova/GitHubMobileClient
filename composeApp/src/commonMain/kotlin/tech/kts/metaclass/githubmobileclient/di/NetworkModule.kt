package tech.kts.metaclass.githubmobileclient.di

import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.plugins.api.createClientPlugin
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.qualifier.named
import org.koin.dsl.module
import tech.kts.metaclass.githubmobileclient.data.network.GitHubApi
import tech.kts.metaclass.githubmobileclient.data.network.GitHubApiImpl
import tech.kts.metaclass.githubmobileclient.data.network.mappers.ApiGitHubRepositoryMapper
import tech.kts.metaclass.githubmobileclient.data.network.mappers.ApiUserMapper
import tech.kts.metaclass.githubmobileclient.data.repositories.TokenRepository

private const val GITHUB_API_VERSION = "2022-11-28"
private const val KTOR_LOG_TAG = "Ktor"

val networkModule = module {
    single(named("auth")) { authHttpClient() }
    single(named("github")) { gitHubHttpClient(get()) }

    single<GitHubApi> {
        GitHubApiImpl(
            get(named("github"))
        )
    }

    factory<ApiUserMapper> { ApiUserMapper() }
    factory<ApiGitHubRepositoryMapper> { ApiGitHubRepositoryMapper(get()) }
}

fun authHttpClient() = HttpClient {
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
            isLenient = true
        })
    }
    install(Logging) {
        logger = object : Logger {
            override fun log(message: String) {
                Napier.d(message, tag = KTOR_LOG_TAG)
            }
        }
        level = LogLevel.HEADERS
    }
}

fun gitHubHttpClient(tokenRepository: TokenRepository): HttpClient {
    val authPlugin = createClientPlugin("AuthPlugin") {
        onRequest { request, _ ->
            val token = tokenRepository.getToken()
            if (token != null) {
                request.headers.append("Authorization", "Bearer $token")
            }
            request.headers.append(name = "X-GitHub-Api-Version", value = GITHUB_API_VERSION)
        }
    }

    return HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Napier.d(message, tag = KTOR_LOG_TAG)
                }
            }
            level = LogLevel.HEADERS
        }

        install(authPlugin)

        defaultRequest {
            url("https://api.github.com/")
            contentType(ContentType.Application.Json)
        }
    }
}