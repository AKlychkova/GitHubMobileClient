package tech.kts.metaclass.githubmobileclient.useCases.profile

import tech.kts.metaclass.githubmobileclient.entities.Profile

interface GetProfileUseCase {
    suspend operator fun invoke(): Result<Profile>
}

class GetProfileUseCaseImpl(
    private val repository: ProfileRepository
): GetProfileUseCase {
    override suspend fun invoke(): Result<Profile> {
        return repository.getCurrentUserProfile()
    }
}