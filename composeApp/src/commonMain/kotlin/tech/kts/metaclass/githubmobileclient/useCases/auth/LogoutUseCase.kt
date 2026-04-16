package tech.kts.metaclass.githubmobileclient.useCases.auth

import tech.kts.metaclass.githubmobileclient.platform.AuthRepository
import tech.kts.metaclass.githubmobileclient.useCases.repositories.ClearRepositoriesCacheUseCase

interface LogoutUseCase {
    suspend operator fun invoke()
}

class LogoutUseCaseImpl(
    private val authRepository: AuthRepository,
    private val clear: ClearRepositoriesCacheUseCase
): LogoutUseCase {
    override suspend fun invoke() {
        clear()
        authRepository.logout()
    }
}