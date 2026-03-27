package tech.kts.metaclass.githubmobileclient.platform

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import tech.kts.metaclass.githubmobileclient.data.database.AppDatabase

private const val DATABASE_NAME = "github-database.db"

fun androidDatabaseBuilder(context: Context): RoomDatabase.Builder<AppDatabase> {
    val dbFile = context.getDatabasePath(DATABASE_NAME)
    return Room.databaseBuilder<AppDatabase>(
        context = context,
        name = dbFile.absolutePath
    )
}