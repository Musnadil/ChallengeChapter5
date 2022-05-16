package com.musnadil.challengechapter5.ui.register

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import com.musnadil.challengechapter5.R
import com.musnadil.challengechapter5.data.room.entity.User
import com.musnadil.challengechapter5.fragment.LoginFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class RegisterViewModel(private val repository: RegisterRepository):ViewModel() {
    private val  _result : MutableLiveData<Long> = MutableLiveData(null)
    val result : LiveData<Long> get() = _result

    fun register(user: User){
        viewModelScope.launch {
            _result.value = repository.register(user)
        }
    }
}
