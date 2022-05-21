package com.musnadil.challengechapter5.data.api

class ApiHelper(private val apiService: ApiService) {
    suspend fun getAllNews(country:String,apiKey:String) = apiService.getAllNews(country, apiKey)
}