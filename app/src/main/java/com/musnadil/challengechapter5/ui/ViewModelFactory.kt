package com.musnadil.challengechapter5.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.musnadil.challengechapter5.data.Repository
import com.musnadil.challengechapter5.ui.auth.AuthViewModel
import com.musnadil.challengechapter5.ui.home.HomeViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val repository: Repository) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)){
            return AuthViewModel(repository) as T
        }
        else if (modelClass.isAssignableFrom(HomeViewModel::class.java)){
            return HomeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class"+modelClass.name)
    }

}