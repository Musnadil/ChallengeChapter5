package com.musnadil.challengechapter5.ui.auth

import com.musnadil.challengechapter5.UserPreferences
import com.musnadil.challengechapter5.data.room.dao.UserDao
import com.musnadil.challengechapter5.data.room.entity.User
import kotlinx.coroutines.flow.Flow

class AuthRepository(private val userDao: UserDao, private val userPreferences: UserPreferences){

    suspend fun register(user:User):Long = userDao.addUser(user)

    fun getUserPref(): Flow<User> {
        return userPreferences.getUserFromPref()
    }

    suspend fun saveToPref(user: User){
        userPreferences.saveUserToPref(user)
    }
}