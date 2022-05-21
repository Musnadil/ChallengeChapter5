package com.musnadil.challengechapter5.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.musnadil.challengechapter5.data.Repository
import com.musnadil.challengechapter5.data.room.entity.User
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: Repository) : ViewModel() {

    private val _user : MutableLiveData<User> = MutableLiveData()
    val user : LiveData<User> get() = _user

    fun setDataUser(user: User) {
        viewModelScope.launch {
            repository.saveToPref(user)
        }
    }

    fun getDataUser(){
        viewModelScope.launch {
            repository.getUserPref().collect {
                _user.value = it
            }
        }
    }

    fun deleteUserPref(){
        viewModelScope.launch{
            repository.deletePref()
        }
    }
}