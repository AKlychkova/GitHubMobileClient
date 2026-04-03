package tech.kts.metaclass.githubmobileclient.useCases.auth

interface CheckTokenExistUseCase {
    suspend operator fun invoke(): Boolean
}

class CheckTokenExistUseCaseImpl(
    private val repository: TokenRepository
): CheckTokenExistUseCase {
    override suspend fun invoke() = repository.getToken() != null
}