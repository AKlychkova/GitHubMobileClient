package tech.kts.metaclass.githubmobileclient.data.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import tech.kts.metaclass.githubmobileclient.data.network.models.ApiGitHubRepository
import tech.kts.metaclass.githubmobileclient.data.network.models.ItemsWrapper
import tech.kts.metaclass.githubmobileclient.data.network.models.Page

interface GitHubApi {
    suspend fun searchRepositories(
        query: String,
        pageNum: Int,
        perPage: Int
    ): Page<ApiGitHubRepository>

}

class GitHubApiImpl(
    private val httpClient: HttpClient
) : GitHubApi {

    override suspend fun searchRepositories(
        query: String,
        pageNum: Int,
        perPage: Int
    ): Page<ApiGitHubRepository> {
        val response = httpClient.get("search/repositories") {
            parameter("q", query)
            parameter("page", pageNum)
            parameter("per_page", perPage)
        }
        val body: ItemsWrapper<ApiGitHubRepository> = response.body()

        return Page(
            items = body.items,
            nextPageNum = response.nextPage(),
            prevPageNum = response.prevPage()
        )
    }

    private fun HttpResponse.nextPage() = this
        .headers["link"]
        ?.split(",")
        ?.firstOrNull { it.contains("rel=\"next\"") }
        ?.let { link ->
            Regex("""(?<=[?&]page=)\d+""").find(link)?.value?.toInt()
        }

    private fun HttpResponse.prevPage() = this
        .headers["link"]
        ?.split(",")
        ?.firstOrNull { it.contains("rel=\"prev\"") }
        ?.let { link ->
            Regex("""(?<=[?&]page=)\d+""").find(link)?.value?.toInt()
        }
}