package tech.kts.metaclass.githubmobileclient.platform

class IosAuthRepository: AuthRepository {
    override suspend fun login(): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun logout() {
        TODO("Not yet implemented")
    }
}