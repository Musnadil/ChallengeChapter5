package com.musnadil.challengechapter5.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.musnadil.challengechapter5.UserManager
import com.musnadil.challengechapter5.room.entity.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    var userLoggedin: MutableLiveData<User> = MutableLiveData()
    private val userManager = UserManager(application)

    val readUsername = userManager.usernameFlow.asLiveData()
    val readPassword = userManager.passwordFlow.asLiveData()
//    fun saveToDataStore(username: String, password: String) = viewModelScope.launch(Dispatchers.IO) {
//        userManager.saveUserToPref(username,password)
//    }
    fun getUser(user: User) {
        userLoggedin.postValue(user)
    }
}