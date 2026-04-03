package tech.kts.metaclass.githubmobileclient.data.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import tech.kts.metaclass.githubmobileclient.useCases.auth.TokenRepository
import tech.kts.metaclass.githubmobileclient.utils.runSuspendCatching

class TokenRepositoryImpl (
    private val dataStore: DataStore<Preferences>
): TokenRepository {

    override suspend fun setToken(token: String): Result<Unit> = runSuspendCatching {
        dataStore.edit { prefs -> prefs[ACCESS_TOKEN] = token }
    }

    override suspend fun getToken(): String? = dataStore.data
        .map { prefs -> prefs[ACCESS_TOKEN] }
        .first()

    override suspend fun clearToken(): Result<Unit> = runSuspendCatching {
        dataStore.edit { prefs -> prefs.remove(ACCESS_TOKEN) }
    }

    companion object {
        private val ACCESS_TOKEN = stringPreferencesKey("access_token")
    }
}