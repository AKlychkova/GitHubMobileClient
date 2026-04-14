package tech.kts.metaclass.githubmobileclient.data.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import tech.kts.metaclass.githubmobileclient.data.network.GitHubApi
import tech.kts.metaclass.githubmobileclient.data.network.mappers.ApiProfileMapper
import tech.kts.metaclass.githubmobileclient.entities.Profile
import tech.kts.metaclass.githubmobileclient.useCases.profile.ProfileRepository
import tech.kts.metaclass.githubmobileclient.utils.runSuspendCatching

class ProfileRepositoryImpl(
    private val api: GitHubApi,
    private val mapper: ApiProfileMapper
): ProfileRepository {
    override suspend fun getCurrentUserProfile(): Result<Profile> = withContext(Dispatchers.IO) {
        runSuspendCatching {
            api.getCurrentUser()
        }.mapCatching(mapper::toDomainModel)
    }
}