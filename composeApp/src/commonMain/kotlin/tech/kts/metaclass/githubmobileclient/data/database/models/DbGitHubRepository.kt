package tech.kts.metaclass.githubmobileclient.data.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import tech.kts.metaclass.githubmobileclient.entities.ProgrammingLanguage

@Entity(
    tableName = "repositories",
    foreignKeys = [
        ForeignKey(
            entity = DbUser::class,
            parentColumns = ["id"],
            childColumns = ["owner_id"]
        )
    ]
)
data class DbGitHubRepository (
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "description")
    val description: String?,

    @ColumnInfo(name = "language")
    val language: ProgrammingLanguage,

    @ColumnInfo(name = "stars_num")
    val stars: Int,

    @ColumnInfo(name = "owner_id")
    val ownerId: Long
)