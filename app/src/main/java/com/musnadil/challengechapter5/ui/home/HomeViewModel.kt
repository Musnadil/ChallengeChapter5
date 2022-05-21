package com.musnadil.challengechapter5.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.musnadil.challengechapter5.data.Repository
import com.musnadil.challengechapter5.data.api.Resource
import com.musnadil.challengechapter5.data.api.model.GetAllNews
import com.musnadil.challengechapter5.data.room.entity.User
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.Response

class HomeViewModel(private val repository: Repository) : ViewModel() {

    private val _user: MutableLiveData<User> = MutableLiveData()
    val user: LiveData<User> get() = _user

    private val _news: MutableLiveData<Resource<Response<GetAllNews>>> = MutableLiveData()
    val news: LiveData<Resource<Response<GetAllNews>>> get() = _news

    fun setDataUser(user: User) {
        viewModelScope.launch {
            repository.saveToPref(user)
        }
    }

    fun getDataUser() {
        viewModelScope.launch {
            repository.getUserPref().collect {
                _user.value = it
            }
        }
    }

    fun deleteUserPref() {
        viewModelScope.launch {
            repository.deletePref()
        }
    }

    fun getNews(country:String, apiKey:String ) {
        viewModelScope.launch {
            _news.postValue(Resource.loading())
            try {
                _news.postValue(Resource.success(repository.getNews(country, apiKey)))
            }catch (exception:Exception){
                _news.postValue(Resource.error(exception.localizedMessage?:"Error occured"))
            }
        }
    }
}