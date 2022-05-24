package com.musnadil.challengechapter5.data.room

import com.musnadil.challengechapter5.data.room.dao.UserDao
import com.musnadil.challengechapter5.data.room.entity.User
import javax.inject.Inject

class DbHelper @Inject constructor(private val userDao: UserDao) {
    suspend fun addUser(user: User): Long = userDao.addUser(user)
    suspend fun getUser(username: String, password: String):User = userDao.getUser(username,password)
    suspend fun updateItem(user: User):Int = userDao.updateItem(user)

}