package tech.kts.metaclass.githubmobileclient.useCases.auth

import tech.kts.metaclass.githubmobileclient.platform.AuthRepository

interface LogoutUseCase {
    suspend operator fun invoke()
}

class LogoutUseCaseImpl(
    private val repository: AuthRepository
): LogoutUseCase {
    override suspend fun invoke() {
        repository.logout()
    }
}