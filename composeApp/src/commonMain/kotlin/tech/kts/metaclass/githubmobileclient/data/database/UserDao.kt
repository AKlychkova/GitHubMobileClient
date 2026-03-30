package tech.kts.metaclass.githubmobileclient.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import tech.kts.metaclass.githubmobileclient.data.database.models.DbUser

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<DbUser>)
}