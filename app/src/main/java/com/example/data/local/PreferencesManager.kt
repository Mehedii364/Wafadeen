package com.example.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "wafadeen_settings")

class PreferencesManager(private val context: Context) {
    companion object {
        val KEY_LANGUAGE = stringPreferencesKey("app_language")
        val KEY_THEME = stringPreferencesKey("app_theme")
        val KEY_ASR_METHOD = stringPreferencesKey("asr_method")
        val KEY_CITY = stringPreferencesKey("selected_city")
        val KEY_VIBRATION = booleanPreferencesKey("vibration_enabled")
        val KEY_SOUND = booleanPreferencesKey("sound_enabled")
        val KEY_FONT_SCALE = floatPreferencesKey("arabic_font_scale")
    }

    val languageFlow: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[KEY_LANGUAGE] ?: "bn"
    }

    val themeFlow: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[KEY_THEME] ?: "system"
    }

    val asrMethodFlow: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[KEY_ASR_METHOD] ?: "Hanafi"
    }

    val cityFlow: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[KEY_CITY] ?: "Dhaka"
    }

    val vibrationFlow: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[KEY_VIBRATION] ?: true
    }

    val soundFlow: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[KEY_SOUND] ?: true
    }

    val fontScaleFlow: Flow<Float> = context.dataStore.data.map { preferences ->
        preferences[KEY_FONT_SCALE] ?: 22f
    }

    suspend fun setLanguage(language: String) {
        context.dataStore.edit { preferences ->
            preferences[KEY_LANGUAGE] = language
        }
    }

    suspend fun setTheme(theme: String) {
        context.dataStore.edit { preferences ->
            preferences[KEY_THEME] = theme
        }
    }

    suspend fun setAsrMethod(method: String) {
        context.dataStore.edit { preferences ->
            preferences[KEY_ASR_METHOD] = method
        }
    }

    suspend fun setCity(city: String) {
        context.dataStore.edit { preferences ->
            preferences[KEY_CITY] = city
        }
    }

    suspend fun setVibration(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[KEY_VIBRATION] = enabled
        }
    }

    suspend fun setSound(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[KEY_SOUND] = enabled
        }
    }

    suspend fun setFontScale(scale: Float) {
        context.dataStore.edit { preferences ->
            preferences[KEY_FONT_SCALE] = scale
        }
    }
}
