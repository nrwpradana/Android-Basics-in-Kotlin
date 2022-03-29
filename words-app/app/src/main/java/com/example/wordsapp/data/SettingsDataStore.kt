package com.example.wordsapp.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

// This is the name of the Preferences Datastore
private const val LAYOUT_PREFERENCES_NAME = "layout_preferences"

// Create a DataStore instance using the preferencesDataStore delegate, with the Context as
// receiver.
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = LAYOUT_PREFERENCES_NAME
)

// create a key that stores a Boolean value that specifies whether the user setting is a linear layout.
private val IS_LINEAR_LAYOUT_MANAGER = booleanPreferencesKey("is_linear_layout_manager")

class SettingsDataStore(context: Context) {

    suspend fun saveLayoutToPreferencesStore(isLinearLayoutManager: Boolean, context: Context) {
        context.dataStore.edit { preferences ->
            preferences[IS_LINEAR_LAYOUT_MANAGER] = isLinearLayoutManager
        }
    }

    /**
     * Preferences DataStore exposes the data stored in a Flow<Preferences>
     * that emits every time a preference has changed.
     *  You don't want to expose the entire Preferences object, just the Boolean value. To do this,
     *  we map the Flow<Preferences>, and get the Boolean value you're interested in.
     * */
    val preferenceFlow: Flow<Boolean> = context.dataStore.data
        // SharedPreference DataStore throws an IOException when an error is encountered while reading data.
        // use the catch() operator to catch the IOException and emit emptyPreferences()
        // since we don't expect any other types of exceptions here, if a different type of exception is thrown, re-throw it.
        .catch { throwable: Throwable ->
            if (throwable is IOException) {
                throwable.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw throwable
            }
        }
        // On the first run of the app, we will use LinearLayoutManager by default
        .map { preferences -> preferences[IS_LINEAR_LAYOUT_MANAGER] ?: true }
}