package com.musnadil.challengechapter5.data.api

import com.musnadil.challengechapter5.data.api.model.GetAllNews
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("top-headlines")
    suspend fun getAllNews(
        @Query("country") country :String,
        @Query("apiKey") apiKey : String
    ) :  Response <GetAllNews>
}