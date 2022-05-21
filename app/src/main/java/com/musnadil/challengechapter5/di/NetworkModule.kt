package com.musnadil.challengechapter5.di

import android.content.Context
import androidx.room.Room
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.musnadil.challengechapter5.data.api.ApiHelper
import com.musnadil.challengechapter5.data.api.ApiService
import com.musnadil.challengechapter5.data.room.dao.UserDao
import com.musnadil.challengechapter5.data.room.database.UserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

private const val BASE_URL = "https://newsapi.org/v2/"

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideLoggingInterceptor():HttpLoggingInterceptor{
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Singleton
    @Provides
    fun provideChuckerInterceptor(
        @ApplicationContext context: Context
    ) :ChuckerInterceptor{
        return ChuckerInterceptor.Builder(context)
            .collector(ChuckerCollector(context))
            .maxContentLength(250000L)
            .redactHeaders(emptySet())
            .alwaysReadResponseBody(false)
            .build()
    }

    @Singleton
    @Provides
    fun provideOkHttp(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        chuckerInterceptor: ChuckerInterceptor
    ):OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(chuckerInterceptor)
            .build()
    }


    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ):Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }


    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit) :ApiService{
        return retrofit.create()
    }

    @Singleton
    @Provides
    fun provideApiHelper(apiService: ApiService): ApiHelper {
        return ApiHelper(apiService)
    }

}