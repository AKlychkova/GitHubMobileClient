package tech.kts.metaclass.githubmobileclient.di

import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
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
import tech.kts.metaclass.githubmobileclient.data.network.mappers.ApiProgrammingLanguageMapper
import tech.kts.metaclass.githubmobileclient.data.network.mappers.ApiUserMapper
import tech.kts.metaclass.githubmobileclient.useCases.auth.TokenRepository

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
    factory<ApiProgrammingLanguageMapper> { ApiProgrammingLanguageMapper() }
    factory<ApiGitHubRepositoryMapper> { ApiGitHubRepositoryMapper(get(), get()) }
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
            level = LogLevel.BODY
        }

        install(Auth) {
            bearer {
                loadTokens {
                    tokenRepository.getToken()?.let { token ->
                        BearerTokens(accessToken = token, refreshToken = null)
                    }
                }
            }
        }

        defaultRequest {
            url("https://api.github.com/")
            contentType(ContentType.Application.Json)
            headers.append(name = "X-GitHub-Api-Version", value = GITHUB_API_VERSION)
        }
    }
}