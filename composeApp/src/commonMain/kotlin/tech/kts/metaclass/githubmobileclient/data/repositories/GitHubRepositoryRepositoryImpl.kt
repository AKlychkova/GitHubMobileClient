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
import tech.kts.metaclass.githubmobileclient.useCases.repositories.SearchResult
import tech.kts.metaclass.githubmobileclient.utils.runSuspendCatching

class GitHubRepositoryRepositoryImpl(
    private val api: GitHubApi,
    private val apiMapper: ApiGitHubRepositoryMapper,
    private val dbMapper: DbGitHubRepositoryMapper,
    private val userDao: UserDao,
    private val repositoryDao: GitHubRepositoryDao
) : GitHubRepositoryRepository {

    override suspend fun searchRepositories(
        query: String,
        pageNum: Int
    ): SearchResult<GitHubRepository> = withContext(Dispatchers.IO) {
        runSuspendCatching {
            api.searchRepositories(query, pageNum, PAGE_SIZE)
        }.fold(
            onSuccess = { page ->
                val domain = page.items.map(apiMapper::toDomainModel)
                saveToDb(domain)
                SearchResult.Success(
                    data = domain,
                    nextPageNum = page.nextPageNum,
                    prevPageNum = page.prevPageNum
                )
            },
            onFailure = { e ->
                val cached = getCached(query)

                if (cached.isNotEmpty()) {
                    SearchResult.Cached(cached, e)
                } else {
                    SearchResult.Failure(e)
                }
            }
        )
    }

    override suspend fun clearCache() {
        userDao.clearData()
    }

    private suspend fun saveToDb(repos: List<GitHubRepository>) {
        val dbModels = repos.map(dbMapper::toDbModel)

        userDao.insertUsers(dbModels.map { it.user }.distinctBy { it.id })
        repositoryDao.insertRepositories(dbModels.map { it.repository })
    }

    private suspend fun getCached(query: String): List<GitHubRepository> {
        return repositoryDao
            .searchRepositoriesWithUsers(query)
            .map(dbMapper::toDomainModel)
    }

    private companion object {
        const val PAGE_SIZE = 30
    }
}
