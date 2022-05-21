package com.musnadil.challengechapter5.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.musnadil.challengechapter5.data.Repository
import com.musnadil.challengechapter5.ui.auth.AuthViewModel
import com.musnadil.challengechapter5.ui.home.HomeViewModel
import com.musnadil.challengechapter5.ui.updateuser.UpdateViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val repository: Repository) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> {
                AuthViewModel(repository) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(repository) as T
            }
            modelClass.isAssignableFrom(UpdateViewModel::class.java) -> {
                UpdateViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel Class" + modelClass.name)
        }
    }

}