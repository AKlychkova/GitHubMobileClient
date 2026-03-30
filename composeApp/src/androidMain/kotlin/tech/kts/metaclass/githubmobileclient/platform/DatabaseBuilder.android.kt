package tech.kts.metaclass.githubmobileclient.platform

import androidx.room.Room
import androidx.room.RoomDatabase
import tech.kts.metaclass.githubmobileclient.data.database.AppDatabase

actual fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    val dbFile = appContext.getDatabasePath("github-database.db")
    return Room.databaseBuilder<AppDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}