package tech.kts.metaclass.githubmobileclient.useCases.profile

import tech.kts.metaclass.githubmobileclient.entities.Profile

interface ProfileRepository {
    suspend fun getCurrentUserProfile(): Result<Profile>
}