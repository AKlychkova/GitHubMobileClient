package tech.kts.metaclass.githubmobileclient.data.network.mappers

import tech.kts.metaclass.githubmobileclient.data.network.models.ApiUser
import tech.kts.metaclass.githubmobileclient.entities.User

class ApiUserMapper {
    fun toDomainModel(user: ApiUser): User {
        return User(
            id = user.id,
            username = user.username,
            avatarUrl = user.avatarUrl
        )
    }
}