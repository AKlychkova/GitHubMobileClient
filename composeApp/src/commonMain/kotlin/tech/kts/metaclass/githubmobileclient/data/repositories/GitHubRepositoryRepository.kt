package tech.kts.metaclass.githubmobileclient.data.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import tech.kts.metaclass.githubmobileclient.data.database.GitHubRepositoryDao
import tech.kts.metaclass.githubmobileclient.data.database.UserDao
import tech.kts.metaclass.githubmobileclient.data.database.mappers.DbGitHubRepositoryMapper
import tech.kts.metaclass.githubmobileclient.data.network.GitHubApi
import tech.kts.metaclass.githubmobileclient.data.network.mappers.ApiGitHubRepositoryMapper
import tech.kts.metaclass.githubmobileclient.entities.GitHubRepository
import kotlin.coroutines.cancellation.CancellationException

// TODO вынести интерфейс в domain слой, чтобы не было зависимости domain -> data
interface GitHubRepositoryRepository {
    suspend fun searchRepositories(query: String): Result<List<GitHubRepository>>
}

class GitHubRepositoryRepositoryImpl(
    private val api: GitHubApi,
    private val apiMapper: ApiGitHubRepositoryMapper,
    private val dbMapper: DbGitHubRepositoryMapper,
    private val userDao: UserDao,
    private val repositoryDao: GitHubRepositoryDao
) : GitHubRepositoryRepository {

    override suspend fun searchRepositories(query: String): Result<List<GitHubRepository>> =
        withContext(Dispatchers.IO) {
            try {
                val remote = api.searchRepositories(query)
                    .items
                    .map(apiMapper::toDomainModel)

                saveToDb(remote)
                Result.success(remote)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                val cached = getCached()

                if (cached.isNotEmpty()) {
                    Result.success(cached)
                } else {
                    Result.failure(e)
                }
            }
        }

    private suspend fun saveToDb(repos: List<GitHubRepository>) {
        val dbModels = repos.map(dbMapper::toDbModel)

        userDao.insertUsers(dbModels.map { it.user }.distinctBy { it.id })
        repositoryDao.insertRepositories(dbModels.map { it.repository })
    }

    private suspend fun getCached(): List<GitHubRepository> {
        return repositoryDao
            .getRepositoriesWithUsers()
            .map(dbMapper::toDomainModel)
    }
}
