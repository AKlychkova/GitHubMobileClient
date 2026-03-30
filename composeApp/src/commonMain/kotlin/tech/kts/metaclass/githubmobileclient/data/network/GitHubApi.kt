package tech.kts.metaclass.githubmobileclient.data.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import tech.kts.metaclass.githubmobileclient.data.network.models.ApiGitHubRepository
import tech.kts.metaclass.githubmobileclient.data.network.models.ApiItemsWrapper

interface GitHubApi {
    suspend fun searchRepositories(query: String): ApiItemsWrapper<ApiGitHubRepository>
}

class GitHubApiImpl(
    private val httpClient: HttpClient
) : GitHubApi {

    override suspend fun searchRepositories(query: String): ApiItemsWrapper<ApiGitHubRepository> {
        return httpClient.get("search/repositories") {
            parameter("q", query)
        }.body()
    }
}