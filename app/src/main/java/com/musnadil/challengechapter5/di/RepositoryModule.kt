package com.musnadil.challengechapter5.di

import com.musnadil.challengechapter5.data.Repository
import com.musnadil.challengechapter5.data.api.ApiHelper
import com.musnadil.challengechapter5.data.datastore.UserPreferences
import com.musnadil.challengechapter5.data.room.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {
    @ViewModelScoped
    @Provides
    fun provideRepository(
        apiHelper: ApiHelper,
        userDao: UserDao,
        userPreferences: UserPreferences
    ) = Repository(apiHelper,userDao,userPreferences)
}