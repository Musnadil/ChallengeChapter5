package com.musnadil.challengechapter5.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.musnadil.challengechapter5.room.entity.User

class HomeViewModel: ViewModel(){
    var userLoggedin : MutableLiveData<User> = MutableLiveData()
    fun getUser(user: User){
        userLoggedin.postValue(user)
    }
}