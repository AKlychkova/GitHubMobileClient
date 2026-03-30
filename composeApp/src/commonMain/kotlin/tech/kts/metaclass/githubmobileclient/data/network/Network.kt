package tech.kts.metaclass.githubmobileclient.data.network

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
import tech.kts.metaclass.githubmobileclient.data.repositories.TokenStorage

object Network {
    private const val GITHUB_API_VERSION = "2022-11-28"
    private val AuthPlugin = createClientPlugin("AuthPlugin") {
        onRequest {
            request, _ ->
            val token = TokenStorage.get()
            if(token != null) {
                request.headers.append("Authorization", "Bearer $token")
            }
            request.headers.append(name = "X-GitHub-Api-Version", value = GITHUB_API_VERSION)
        }
    }
    val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Napier.d(message, tag = "Ktor")
                }
            }
            level = LogLevel.HEADERS
        }

        install (AuthPlugin)

        defaultRequest {
            url("https://api.github.com/")
            contentType(ContentType.Application.Json)
        }
    }

    val authHttpClient = HttpClient{
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Napier.d(message, tag = "Ktor")
                }
            }
            level = LogLevel.HEADERS
        }
    }
}