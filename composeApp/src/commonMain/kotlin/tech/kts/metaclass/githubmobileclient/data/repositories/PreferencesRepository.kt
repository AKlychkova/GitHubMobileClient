package tech.kts.metaclass.githubmobileclient.data.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import tech.kts.metaclass.githubmobileclient.utils.runSuspendCatching

interface PreferencesRepository {
    suspend fun shouldShowStartScreen(): Boolean
    suspend fun toggleStartScreen(isShown: Boolean): Result<Unit>
}

class PreferencesRepositoryImpl(
    private val dataStore: DataStore<Preferences>
): PreferencesRepository {

    override suspend fun shouldShowStartScreen(): Boolean =
        dataStore.data
            .map { prefs -> prefs[SHOW_START_SCREEN] ?: true }
            .distinctUntilChanged()
            .first()

    override suspend fun toggleStartScreen(isShown: Boolean): Result<Unit> = runSuspendCatching {
        dataStore.edit { prefs -> prefs[SHOW_START_SCREEN] = isShown }
    }

    companion object {
        private val SHOW_START_SCREEN = booleanPreferencesKey("show_start")
    }
}