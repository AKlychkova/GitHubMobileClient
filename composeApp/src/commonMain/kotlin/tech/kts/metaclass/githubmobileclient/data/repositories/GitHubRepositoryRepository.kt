package tech.kts.metaclass.githubmobileclient.data.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import tech.kts.metaclass.githubmobileclient.data.network.GitHubApi
import tech.kts.metaclass.githubmobileclient.data.network.GitHubApiImpl
import tech.kts.metaclass.githubmobileclient.data.network.Network
import tech.kts.metaclass.githubmobileclient.data.network.mappers.ApiGitHubRepositoryMapper
import tech.kts.metaclass.githubmobileclient.data.network.mappers.ApiOwnerMapper
import tech.kts.metaclass.githubmobileclient.entities.GitHubRepository
import tech.kts.metaclass.githubmobileclient.utils.runSuspendCatching

// TODO вынести интерфейс в domain слой, чтобы не было зависимости domain -> data
interface GitHubRepositoryRepository {
    suspend fun searchRepositories(query: String): Result<List<GitHubRepository>>
}

class GitHubRepositoryRepositoryImpl(
    private val api: GitHubApi = GitHubApiImpl(Network.httpClient), // TODO: в di контейнер
    private val mapper: ApiGitHubRepositoryMapper = ApiGitHubRepositoryMapper(ApiOwnerMapper()) // TODO: в di контейнер
) : GitHubRepositoryRepository {

    override suspend fun searchRepositories(query: String): Result<List<GitHubRepository>> {
        return runSuspendCatching {
            withContext(Dispatchers.IO) {
                api.searchRepositories(query)
                    .items
                    .map(mapper::toDomainModel)
            }
        }
    }
}
