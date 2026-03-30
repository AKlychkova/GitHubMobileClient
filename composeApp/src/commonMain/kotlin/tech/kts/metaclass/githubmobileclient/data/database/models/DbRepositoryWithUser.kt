package tech.kts.metaclass.githubmobileclient.data.database.models

import androidx.room.Embedded
import androidx.room.Relation

data class DbRepositoryWithUser(
    @Embedded val repository: DbGitHubRepository,

    @Relation(
        parentColumn = "owner_id",
        entityColumn = "id"
    )
    val user: DbUser
)