package com.musnadil.challengechapter5.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.musnadil.challengechapter5.UserManager
import com.musnadil.challengechapter5.room.entity.User

class HomeViewModel(private val pref: UserManager) : ViewModel() {

    suspend fun setDataUser(user: User) {
        pref.saveUserToPref(user)
    }

    fun getDataUser(): LiveData<User> {
        return pref.getUserFromPref().asLiveData()
    }
}