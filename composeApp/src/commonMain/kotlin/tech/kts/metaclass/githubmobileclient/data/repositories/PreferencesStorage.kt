package tech.kts.metaclass.githubmobileclient.data.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import tech.kts.metaclass.githubmobileclient.di.DataStoreProvider
import tech.kts.metaclass.githubmobileclient.utils.runSuspendCatching

object PreferencesStorage {
    private val dataStore: DataStore<Preferences> = DataStoreProvider.instance
    suspend fun shouldShowStartScreen(): Boolean =
        dataStore.data
            .map { prefs -> prefs[SHOW_START_SCREEN] ?: true }
            .distinctUntilChanged()
            .first()

    suspend fun toggleStartScreen(isShown: Boolean): Result<Unit> = runSuspendCatching {
        dataStore.edit { prefs -> prefs[SHOW_START_SCREEN] = isShown }
    }

    val SHOW_START_SCREEN = booleanPreferencesKey("show_start")
}