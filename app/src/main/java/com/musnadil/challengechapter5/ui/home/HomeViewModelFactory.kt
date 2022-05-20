package com.musnadil.challengechapter5.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory(private val repository: HomeRepository) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)){
            return HomeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class: ")
    }
}