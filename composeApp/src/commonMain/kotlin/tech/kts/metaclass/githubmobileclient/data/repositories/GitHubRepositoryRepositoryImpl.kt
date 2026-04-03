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
import tech.kts.metaclass.githubmobileclient.useCases.repositories.GitHubRepositoryRepository
import tech.kts.metaclass.githubmobileclient.useCases.repositories.SearchRepositoriesResult
import tech.kts.metaclass.githubmobileclient.utils.runSuspendCatching

class GitHubRepositoryRepositoryImpl(
    private val api: GitHubApi,
    private val apiMapper: ApiGitHubRepositoryMapper,
    private val dbMapper: DbGitHubRepositoryMapper,
    private val userDao: UserDao,
    private val repositoryDao: GitHubRepositoryDao
) : GitHubRepositoryRepository {

    override suspend fun searchRepositories(
        query: String
    ): SearchRepositoriesResult = withContext(Dispatchers.IO) {
        runSuspendCatching {
            api.searchRepositories(query)
                .items
                .map(apiMapper::toDomainModel)
        }.fold(
            onSuccess = { remote ->
                saveToDb(remote)
                SearchRepositoriesResult.Success(remote)
            },
            onFailure = { e ->
                val cached = getCached()

                if (cached.isNotEmpty()) {
                    SearchRepositoriesResult.Cached(cached, e)
                } else {
                    SearchRepositoriesResult.Failure(e)
                }
            }
        )
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
