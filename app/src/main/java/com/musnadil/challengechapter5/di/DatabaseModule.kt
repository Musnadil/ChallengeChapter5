package com.musnadil.challengechapter5.di

import android.content.Context
import androidx.room.Room
import com.musnadil.challengechapter5.data.room.dao.UserDao
import com.musnadil.challengechapter5.data.room.database.UserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideUserDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, UserDatabase::class.java, UserDatabase.DB_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideUserDAO(userDatabase: UserDatabase): UserDao {
        return userDatabase.userDao()
    }

}