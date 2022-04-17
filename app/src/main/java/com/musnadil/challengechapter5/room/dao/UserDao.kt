package com.musnadil.challengechapter5.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.musnadil.challengechapter5.room.entity.User

@Dao
interface UserDao {
    @Query("SELECT * FROM User WHERE username like :username and password like :password")
    fun userCheck(username: String, password: String):Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUser(user: User): Long
}