package com.musnadil.challengechapter5.ui.auth


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.musnadil.challengechapter5.data.Repository
import com.musnadil.challengechapter5.data.room.entity.User
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: Repository):ViewModel() {
    private val  _resultRegister : MutableLiveData<Long> = MutableLiveData()
    val resultRegister : LiveData<Long> get() = _resultRegister

    private val  _resultLogin : MutableLiveData<User> = MutableLiveData()
    val resultLogin : LiveData<User> get() = _resultLogin

    private val _user : MutableLiveData<User> = MutableLiveData()
    val user : LiveData<User> get() = _user


    fun login(username:String, password:String){
        viewModelScope.launch {
            _resultLogin.value = repository.login(username, password)
        }
    }

    fun register(user: User){
        viewModelScope.launch {
            _resultRegister.value = repository.register(user)
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
