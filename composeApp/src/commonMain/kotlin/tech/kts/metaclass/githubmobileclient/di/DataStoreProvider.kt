package tech.kts.metaclass.githubmobileclient.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath
import tech.kts.metaclass.githubmobileclient.platform.getFilesDir

object DataStoreProvider {
    private const val DATA_STORE_FILE_NAME = "settings.preferences_pb"

    val instance: DataStore<Preferences> by lazy {
        PreferenceDataStoreFactory.createWithPath(
            produceFile = { "${getFilesDir()}/$DATA_STORE_FILE_NAME".toPath() }
        )
    }
}