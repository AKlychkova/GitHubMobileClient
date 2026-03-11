package tech.kts.metaclass.githubmobileclient.data.network.mappers

import tech.kts.metaclass.githubmobileclient.data.network.models.ApiOwner
import tech.kts.metaclass.githubmobileclient.entities.Owner

class ApiOwnerMapper {
    fun toDomainModel(owner: ApiOwner): Owner {
        return Owner(
            id = owner.id,
            username = owner.username,
            avatarUrl = owner.avatarUrl
        )
    }
}