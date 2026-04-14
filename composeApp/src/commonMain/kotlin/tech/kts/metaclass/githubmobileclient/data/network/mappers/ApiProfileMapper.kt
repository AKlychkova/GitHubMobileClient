package tech.kts.metaclass.githubmobileclient.data.network.mappers

import tech.kts.metaclass.githubmobileclient.data.network.models.ApiProfile
import tech.kts.metaclass.githubmobileclient.entities.Email
import tech.kts.metaclass.githubmobileclient.entities.Profile

class ApiProfileMapper {
    fun toDomainModel(profile: ApiProfile): Profile {
        return Profile(
            login = profile.login,
            name = profile.name,
            bio = profile.bio,
            location = profile.location,
            followers = profile.followers,
            company = profile.company,
            email = profile.email?.let { Email(it) },
            avatarUrl = profile.avatarUrl,
            repositoriesCount = profile.publicRepoCount + profile.privateRepoCount
        )
    }
}