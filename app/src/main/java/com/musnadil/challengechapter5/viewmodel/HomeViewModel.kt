package com.musnadil.challengechapter5.viewmodel

import androidx.lifecycle.*
import com.musnadil.challengechapter5.HomeRepository
import com.musnadil.challengechapter5.UserPreferences
import com.musnadil.challengechapter5.data.room.entity.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: HomeRepository) : ViewModel() {

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