package tech.kts.metaclass.githubmobileclient.data.repositories

import io.ktor.utils.io.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import tech.kts.metaclass.githubmobileclient.data.network.GitHubRemoteDataSource
import tech.kts.metaclass.githubmobileclient.data.network.GitHubRemoteDataSourceImpl
import tech.kts.metaclass.githubmobileclient.data.network.Network
import tech.kts.metaclass.githubmobileclient.data.network.mappers.ApiGitHubRepositoryMapper
import tech.kts.metaclass.githubmobileclient.data.network.mappers.ApiOwnerMapper
import tech.kts.metaclass.githubmobileclient.entities.GitHubRepository

// TODO вынести интерфейс в domain слой, чтобы не было зависимости domain -> data
interface GitHubRepositoryRepository {
    suspend fun searchRepositories(query: String): Result<List<GitHubRepository>>
}

class GitHubRepositoryRepositoryImpl(
    private val remoteDataSource: GitHubRemoteDataSource = GitHubRemoteDataSourceImpl(Network.httpClient), // TODO: в di контейнер
    private val mapper: ApiGitHubRepositoryMapper = ApiGitHubRepositoryMapper(ApiOwnerMapper()) // TODO: в di контейнер
) : GitHubRepositoryRepository {

    override suspend fun searchRepositories(query: String): Result<List<GitHubRepository>> {
        return withContext(Dispatchers.IO) {
            try {
                Result.success(remoteDataSource.searchRepositories(query)
                    .items
                    .map(mapper::toDomainModel)
                )
            } catch(e: CancellationException) {
                throw e
            } catch(e: Exception) {
                Result.failure(e)
            }
        }
    }
}
