package com.musnadil.challengechapter5.data.api.service

import com.musnadil.challengechapter5.data.api.model.GetAllNews
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("top-headlines")
    fun getAllNews(
        @Query("country") country :String,
        @Query("apiKey") apiKey : String
    ) : Call<GetAllNews>
}