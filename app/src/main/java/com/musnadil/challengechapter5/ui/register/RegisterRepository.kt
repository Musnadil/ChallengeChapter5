package com.musnadil.challengechapter5.ui.register

import com.musnadil.challengechapter5.data.room.dao.UserDao
import com.musnadil.challengechapter5.data.room.entity.User

class RegisterRepository(private val userDao: UserDao){

    suspend fun register(user:User):Long = userDao.addUser(user)
}