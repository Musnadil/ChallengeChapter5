package com.musnadil.challengechapter5.data.room.dao

import androidx.room.*
import com.musnadil.challengechapter5.data.room.entity.User

@Dao
interface UserDao {

    //login
    @Query("SELECT * FROM User WHERE username like :username and password like :password")
    suspend fun getUser(username: String, password: String):User

    //register
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(user: User): Long

    //update
    @Update
    suspend fun updateUser(user: User):Int
}