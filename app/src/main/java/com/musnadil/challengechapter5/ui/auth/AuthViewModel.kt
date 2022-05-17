package com.musnadil.challengechapter5.ui.auth


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.musnadil.challengechapter5.data.room.entity.User
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: AuthRepository):ViewModel() {
    private val  _result : MutableLiveData<Long> = MutableLiveData()
    val result : LiveData<Long> get() = _result

    private val _user : MutableLiveData<User> = MutableLiveData()
    val user : LiveData<User> get() = _user

    fun register(user: User){
        viewModelScope.launch {
            _result.value = repository.register(user)
        }
    }
    fun getDataUser(){
        viewModelScope.launch {
            repository.getUserPref().collect {
                _user.value = it
            }
        }
    }
    fun setDataUser(user: User) {
        viewModelScope.launch {
            repository.saveToPref(user)
        }
    }
}
