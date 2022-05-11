package com.musnadil.challengechapter5

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.musnadil.challengechapter5.room.entity.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserManager(private val context: Context) {
    companion object {
        const val USERPREF = "USER_PREFS"
        private val ID_USER_KEY = intPreferencesKey("ID_USER_KEY")
        val USERNAME_KEY = stringPreferencesKey("USERNAME_KEY")
        private val EMAIL_KEY = stringPreferencesKey("EMAIL_KEY")
        val PASSWORD_KEY = stringPreferencesKey("PASSWORD_KEY")
        const val DEFAULT_ID = -1
        const val DEFAULT_USERNAME = "DEF_USERNAME"
        const val DEFAULT_EMAIL = "DEF_EMAIL"
        const val DEFAULT_PASSWORD = "DEF_PASSWORD"
        val Context.dataStore by preferencesDataStore(UserManager.USERPREF)
    }

    suspend fun setUser(user: User) {
        context.dataStore.edit { preferences ->
            preferences[ID_USER_KEY] = user.id!!.toInt()
            preferences[USERNAME_KEY] = user.username
            preferences[EMAIL_KEY] = user.email
            preferences[PASSWORD_KEY] = user.password
        }
    }

    fun getUser(): Flow<User> {
        return context.dataStore.data.map { preferences ->
            User(
                preferences[ID_USER_KEY] ?: DEFAULT_ID,
                preferences[USERNAME_KEY] ?: DEFAULT_USERNAME,
                preferences[EMAIL_KEY] ?: DEFAULT_EMAIL,
                preferences[PASSWORD_KEY] ?: DEFAULT_PASSWORD
            )
        }
    }

    suspend fun deleteUserFromPref() {
        context.dataStore.edit {
            it.clear()
        }
    }

}