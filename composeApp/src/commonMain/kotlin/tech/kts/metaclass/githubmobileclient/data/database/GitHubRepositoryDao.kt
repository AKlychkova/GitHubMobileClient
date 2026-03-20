package tech.kts.metaclass.githubmobileclient.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import tech.kts.metaclass.githubmobileclient.data.database.models.DbGitHubRepository
import tech.kts.metaclass.githubmobileclient.data.database.models.RepositoryWithUser

@Dao
interface GitHubRepositoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepositories(repositories: List<DbGitHubRepository>)

    @Transaction
    @Query("SELECT * FROM repositories")
    suspend fun getRepositoriesWithUsers(): List<RepositoryWithUser>

    @Query("DELETE FROM repositories")
    suspend fun clearRepositories()
}