package tech.kts.metaclass.githubmobileclient.data.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import tech.kts.metaclass.githubmobileclient.data.auth.AuthToken
import tech.kts.metaclass.githubmobileclient.di.DataStoreProvider
import tech.kts.metaclass.githubmobileclient.utils.runSuspendCatching

object TokenStorage {
    private val dataStore: DataStore<Preferences> = DataStoreProvider.instance

    suspend fun set(token: AuthToken): Result<Unit> = runSuspendCatching {
        dataStore.edit { prefs -> prefs[ACCESS_TOKEN] = token.accessToken }
    }

    suspend fun get(): String? = dataStore.data
        .map { prefs -> prefs[ACCESS_TOKEN] }
        .first()

    suspend fun clearToken(): Result<Unit> = runSuspendCatching {
        dataStore.edit { prefs -> prefs.remove(ACCESS_TOKEN) }
    }

    private val ACCESS_TOKEN = stringPreferencesKey("access_token")
}