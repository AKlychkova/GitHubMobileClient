package tech.kts.metaclass.githubmobileclient.platform

import androidx.room.RoomDatabase
import tech.kts.metaclass.githubmobileclient.data.database.AppDatabase

expect fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase>