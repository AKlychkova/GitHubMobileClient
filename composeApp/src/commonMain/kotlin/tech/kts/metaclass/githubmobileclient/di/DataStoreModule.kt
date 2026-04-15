package tech.kts.metaclass.githubmobileclient.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath
import org.koin.dsl.module
import tech.kts.metaclass.githubmobileclient.data.storage.OnboardingStorage
import tech.kts.metaclass.githubmobileclient.data.storage.OnboardingStorageImpl
import tech.kts.metaclass.githubmobileclient.platform.DirectoriesProvider

val dataStoreModule = module {
    single<DataStore<Preferences>> { preferencesDataStore(get()) }
    single<OnboardingStorage> { OnboardingStorageImpl(get()) }
}

fun preferencesDataStore(dirProvider: DirectoriesProvider): DataStore<Preferences> {
    val fileName = "settings.preferences_pb"
    val dirPath = dirProvider.getFilesDir()
    return PreferenceDataStoreFactory.createWithPath(
        produceFile = { "$dirPath/$fileName".toPath() }
    )
}