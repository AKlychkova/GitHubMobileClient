package tech.kts.metaclass.githubmobileclient.di

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import org.koin.dsl.module
import tech.kts.metaclass.githubmobileclient.data.database.AppDatabase
import tech.kts.metaclass.githubmobileclient.data.database.GitHubRepositoryDao
import tech.kts.metaclass.githubmobileclient.data.database.UserDao
import tech.kts.metaclass.githubmobileclient.data.database.mappers.DbGitHubRepositoryMapper

val dataBaseModule = module {
    single<AppDatabase> { dataBase(get()) }
    single<UserDao> { userDao(get()) }
    single<GitHubRepositoryDao> { repositoryDao(get()) }

    factory<DbGitHubRepositoryMapper> { DbGitHubRepositoryMapper() }
}

fun dataBase(builder: RoomDatabase.Builder<AppDatabase>): AppDatabase {
    return builder
        .setDriver(BundledSQLiteDriver())
        .build()
}

fun userDao(db: AppDatabase): UserDao = db.getUserDao()

fun repositoryDao(db: AppDatabase): GitHubRepositoryDao = db.getRepositoryDao()