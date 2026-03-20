package tech.kts.metaclass.githubmobileclient.data.database.mappers

import tech.kts.metaclass.githubmobileclient.data.database.models.DbGitHubRepository
import tech.kts.metaclass.githubmobileclient.data.database.models.DbUser
import tech.kts.metaclass.githubmobileclient.data.database.models.RepositoryWithUser
import tech.kts.metaclass.githubmobileclient.entities.GitHubRepository
import tech.kts.metaclass.githubmobileclient.entities.User

class DbGitHubRepositoryMapper {
    fun toDbModel(domain: GitHubRepository): Pair<DbGitHubRepository, DbUser> {
        return DbGitHubRepository(
            id = domain.id,
            name = domain.name,
            description = domain.description,
            language = domain.language,
            stars = domain.stars,
            ownerId = domain.owner.id
        ) to DbUser(
            id = domain.owner.id,
            username = domain.owner.username,
            avatarUrl = domain.owner.avatarUrl
        )
    }

    fun toDomainModel(dbModel: RepositoryWithUser): GitHubRepository {
        return GitHubRepository(
            id = dbModel.repository.id,
            name = dbModel.repository.name,
            description = dbModel.repository.description,
            language = dbModel.repository.language,
            stars = dbModel.repository.stars,
            owner = User(
                id = dbModel.user.id,
                username = dbModel.user.username,
                avatarUrl = dbModel.user.avatarUrl
            )
        )
    }
}