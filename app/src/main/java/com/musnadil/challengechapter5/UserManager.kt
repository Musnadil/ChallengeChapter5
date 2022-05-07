package com.musnadil.challengechapter5

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.clear
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserManager(context: Context) {
    companion object {
        const val USERPREF = "USER_PREFS"
        val USERNAME_KEY = preferencesKey<String>("USERNAME")
        val PASSWORD_KEY = preferencesKey<String>("PASSWORD")
        const val DEFAULT_USERNAME = "DEF_USERNAME"
        const val DEFAULT_PASSWORD = "DEF_PASSWORD"
    }

    private val dataStore: DataStore<Preferences> = context.createDataStore(name = USERPREF)

    suspend fun saveUserToPref(username: String, password: String) {
        dataStore.edit {
            it[USERNAME_KEY] = username
            it[PASSWORD_KEY] = password
        }
    }
    suspend fun deleteUserFromPref(){
        dataStore.edit {
            it.clear()
        }
    }
    val usernameFlow: Flow<String> = dataStore.data.map {
        it[USERNAME_KEY] ?: DEFAULT_USERNAME
    }
    val passwordFlow: Flow<String> = dataStore.data.map {
        it[PASSWORD_KEY] ?: DEFAULT_PASSWORD
    }
}