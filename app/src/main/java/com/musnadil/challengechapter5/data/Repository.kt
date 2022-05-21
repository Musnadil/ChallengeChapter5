package com.musnadil.challengechapter5.data

import com.musnadil.challengechapter5.data.api.ApiHelper
import com.musnadil.challengechapter5.data.api.ApiService
import com.musnadil.challengechapter5.data.datastore.UserPreferences
import com.musnadil.challengechapter5.data.room.dao.UserDao
import com.musnadil.challengechapter5.data.room.entity.User
import kotlinx.coroutines.flow.Flow

class Repository(
    private val apiHelper: ApiHelper,
    private val userDao: UserDao,
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
    suspend fun register(user: User): Long = userDao.addUser(user)

    suspend fun login(username: String, password: String): User {
        return userDao.getUser(username, password)
    }

    suspend fun update(user: User): Int = userDao.updateItem(user)

    //api
    suspend fun getNews(country: String, apiKey: String) = apiHelper.getAllNews(country, apiKey)
}

