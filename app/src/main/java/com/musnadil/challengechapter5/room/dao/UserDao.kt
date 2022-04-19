package com.musnadil.challengechapter5.room.dao

import androidx.room.*
import com.musnadil.challengechapter5.room.entity.User

@Dao
interface UserDao {
    @Query("SELECT * FROM User WHERE username like :username and password like :password")
    fun userCheck(username: String, password: String):Boolean

    @Query("SELECT * FROM User WHERE username like :username and password like :password")
    fun getUser(username: String, password: String):User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUser(user: User): Long

    @Update
    fun updateItem(user: User):Int
}