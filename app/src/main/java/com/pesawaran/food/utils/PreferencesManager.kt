package com.pesawaran.food.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "pesawaran_food_preferences")

class PreferencesManager(private val context: Context) {

    private val dataStore = context.dataStore

    // Auth
    fun saveUserId(userId: String) = saveStringPreference(Constants.PREF_USER_ID, userId)
    fun getUserId(): Flow<String> = getStringPreference(Constants.PREF_USER_ID)

    fun saveUserRole(role: String) = saveStringPreference(Constants.PREF_USER_ROLE, role)
    fun getUserRole(): Flow<String> = getStringPreference(Constants.PREF_USER_ROLE)

    fun saveIsLoggedIn(isLoggedIn: Boolean) = saveBooleanPreference(Constants.PREF_IS_LOGGED_IN, isLoggedIn)
    fun isLoggedIn(): Flow<Boolean> = getBooleanPreference(Constants.PREF_IS_LOGGED_IN)

    fun saveOnboardingCompleted(isCompleted: Boolean) = saveBooleanPreference(Constants.PREF_IS_ONBOARDING_COMPLETED, isCompleted)
    fun isOnboardingCompleted(): Flow<Boolean> = getBooleanPreference(Constants.PREF_IS_ONBOARDING_COMPLETED)

    // Settings
    fun saveDarkMode(isDarkMode: Boolean) = saveBooleanPreference(Constants.PREF_DARK_MODE, isDarkMode)
    fun getDarkMode(): Flow<Boolean> = getBooleanPreference(Constants.PREF_DARK_MODE)

    fun saveLanguage(language: String) = saveStringPreference(Constants.PREF_LANGUAGE, language)
    fun getLanguage(): Flow<String> = getStringPreference(Constants.PREF_LANGUAGE)

    fun saveDeviceToken(token: String) = saveStringPreference(Constants.PREF_DEVICE_TOKEN, token)
    fun getDeviceToken(): Flow<String> = getStringPreference(Constants.PREF_DEVICE_TOKEN)

    fun saveLastLocation(latitude: Double, longitude: Double) {
        saveStringPreference(Constants.PREF_LAST_LOCATION, "$latitude,$longitude")
    }
    fun getLastLocation(): Flow<String> = getStringPreference(Constants.PREF_LAST_LOCATION)

    // Generic Functions
    private fun saveStringPreference(key: String, value: String) = context.dataStore.edit { preferences ->
        preferences[stringPreferencesKey(key)] = value
    }

    private fun getStringPreference(key: String): Flow<String> = context.dataStore.data.map { preferences ->
        preferences[stringPreferencesKey(key)] ?: ""
    }

    private fun saveBooleanPreference(key: String, value: Boolean) = context.dataStore.edit { preferences ->
        preferences[booleanPreferencesKey(key)] = value
    }

    private fun getBooleanPreference(key: String): Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[booleanPreferencesKey(key)] ?: false
    }

    private fun saveIntPreference(key: String, value: Int) = context.dataStore.edit { preferences ->
        preferences[intPreferencesKey(key)] = value
    }

    private fun getIntPreference(key: String): Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[intPreferencesKey(key)] ?: 0
    }

    suspend fun clearAllPreferences() {
        context.dataStore.edit { it.clear() }
    }
}