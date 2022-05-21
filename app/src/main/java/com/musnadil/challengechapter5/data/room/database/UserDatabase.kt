package com.musnadil.challengechapter5.data.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.musnadil.challengechapter5.data.room.dao.UserDao
import com.musnadil.challengechapter5.data.room.entity.User

@Database(entities = [User::class], version = 1)
abstract class UserDatabase: RoomDatabase(){
    abstract fun userDao() : UserDao

    companion object{
        const val DB_NAME = "Store.db"
        private var INSTANCE: UserDatabase? = null

        fun getInstance(context: Context):UserDatabase{
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    DB_NAME
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}
