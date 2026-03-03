package tech.kts.metaclass.githubmobileclient.data.repositories

import kotlin.random.Random

// ??? Стоит потом вынести интерфейс в domain слой, чтобы не было зависимости domain -> data ???
interface LoginRepository {
    suspend fun login(username: String, password: String) : Result<Unit>
}

class LoginRepositoryImpl: LoginRepository {
    override suspend fun login(
        username: String,
        password: String
    ): Result<Unit> {
        // TODO: Убрать заглушку
        val isSuccess = Random.nextBoolean()

        if (isSuccess) {
            return Result.success(Unit)
        } else {
            // TODO: свой exception
            return Result.failure(RuntimeException("Authentication Exception"))
        }
    }

}