package com.musnadil.challengechapter5.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.preferencesDataStoreFile
import com.musnadil.challengechapter5.data.datastore.UserPreferences
import com.musnadil.challengechapter5.data.datastore.UserPreferences.Companion.dataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {
    @Provides
    @Singleton
//    fun dataStore(@ApplicationContext context:Context): DataStore<Preferences>{
//        return PreferenceDataStoreFactory.create(
////            corruptionHandler = ReplaceFileCorruptionHandler(
////                produceNewData = { emptyPreferences()}
////            ),
////            migrations = listOf(SharedPreferencesMigration(context,UserPreferences.USERPREF)),
////            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
//            produceFile = {context.preferencesDataStoreFile(UserPreferences.USERPREF)}
//        )
//    }

    fun provideUserPreference(@ApplicationContext context: Context) = UserPreferences(context)
}