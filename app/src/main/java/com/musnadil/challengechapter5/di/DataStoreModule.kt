package com.musnadil.challengechapter5.di

import android.content.Context
import com.musnadil.challengechapter5.data.datastore.UserPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {
    @Provides
    @Singleton
    fun provideUserPreference(@ApplicationContext context: Context) = UserPreferences(context)
}