package tech.kts.metaclass.githubmobileclient.data.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

interface OnboardingStorage {
    suspend fun isOnboardingCompleted(): Boolean
    suspend fun setOnboardingCompleted()
}

class OnboardingStorageImpl(
    private val dataStore: DataStore<Preferences>,
) : OnboardingStorage {

    override suspend fun isOnboardingCompleted(): Boolean =
        dataStore.data
            .map { prefs -> prefs[ONBOARDING_COMPLETED_KEY] ?: false }
            .distinctUntilChanged()
            .first()

    override suspend fun setOnboardingCompleted() {
        dataStore.edit { prefs -> prefs[ONBOARDING_COMPLETED_KEY] = true }
    }

    companion object {
        private val ONBOARDING_COMPLETED_KEY = booleanPreferencesKey("onboarding_completed")
    }
}