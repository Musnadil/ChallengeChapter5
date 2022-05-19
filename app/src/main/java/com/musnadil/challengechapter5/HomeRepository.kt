package com.musnadil.challengechapter5

import com.musnadil.challengechapter5.data.room.entity.User
import kotlinx.coroutines.flow.Flow

class HomeRepository(private val userPreferences: UserPreferences) {

    suspend fun saveToPref(user: User) {
        userPreferences.saveUserToPref(user)
    }

    fun getUserPref(): Flow<User> {
        return userPreferences.getUserFromPref()
    }

    suspend fun deletePref() {
        userPreferences.deleteUserFromPref()
    }
}