package com.musnadil.challengechapter5.data

import com.musnadil.challengechapter5.data.api.ApiHelper
import com.musnadil.challengechapter5.data.datastore.UserPreferences
import com.musnadil.challengechapter5.data.room.DbHelper
import com.musnadil.challengechapter5.data.room.entity.User
import kotlinx.coroutines.flow.Flow

class Repository(
    private val apiHelper: ApiHelper,
    private val dbHelper: DbHelper,
    private val userPreferences: UserPreferences
) {

    //data store
    suspend fun saveToPref(user: User) {
        userPreferences.saveUserToPref(user)
    }

    fun getUserPref(): Flow<User> {
        return userPreferences.getUserFromPref()
    }

    suspend fun deletePref() {
        userPreferences.deleteUserFromPref()
    }

    //room
    suspend fun register(user: User): Long = dbHelper.addUser(user)

    suspend fun login(username: String, password: String): User {
        return dbHelper.getUser(username, password)
    }

    suspend fun update(user: User): Int = dbHelper.updateUser(user)

    //api
    suspend fun getNews(country: String, apiKey: String) = apiHelper.getAllNews(country, apiKey)
}

