package tech.kts.metaclass.githubmobileclient.useCases.auth

import tech.kts.metaclass.githubmobileclient.platform.AuthRepository

interface LoginUseCase {
    suspend operator fun invoke(): Result<Unit>
}

class LoginUseCaseImpl(
    private val repository: AuthRepository
) : LoginUseCase {
    override suspend fun invoke(): Result<Unit> {
        return repository.login()
    }
}