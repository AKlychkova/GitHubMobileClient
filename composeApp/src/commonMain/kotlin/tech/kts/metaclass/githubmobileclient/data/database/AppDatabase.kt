package tech.kts.metaclass.githubmobileclient.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import tech.kts.metaclass.githubmobileclient.data.database.converters.ProgrammingLanguageConverter
import tech.kts.metaclass.githubmobileclient.data.database.models.DbGitHubRepository
import tech.kts.metaclass.githubmobileclient.data.database.models.DbUser
import tech.kts.metaclass.githubmobileclient.platform.getDatabaseBuilder

@Database(
    entities = [DbUser::class, DbGitHubRepository::class],
    version = 1
)
@TypeConverters(
    ProgrammingLanguageConverter::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getUserDao(): UserDao
    abstract fun getRepositoryDao(): GitHubRepositoryDao
}

object DatabaseProvider {
    val instance: AppDatabase by lazy {
        getDatabaseBuilder()
            .setDriver(BundledSQLiteDriver())
            .build()
    }
}