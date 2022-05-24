package com.musnadil.challengechapter5.data

import com.musnadil.challengechapter5.data.api.ApiHelper
import com.musnadil.challengechapter5.data.api.ApiService
import com.musnadil.challengechapter5.data.api.model.GetAllNews
import com.musnadil.challengechapter5.data.datastore.UserPreferences
import com.musnadil.challengechapter5.data.room.DbHelper
import com.musnadil.challengechapter5.data.room.dao.UserDao
import com.musnadil.challengechapter5.data.room.entity.User
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import retrofit2.Response

class RepositoryTest {

    //collaborator
    private lateinit var apiService: ApiService
    private lateinit var apiHelper: ApiHelper
    private lateinit var dbHelper: DbHelper
    private lateinit var userPreferences: UserPreferences

    //system under test
    private lateinit var repository: Repository


    @Before
    fun setUp() {
        apiService = mockk()
        dbHelper = mockk()
        userPreferences = mockk()
        apiHelper = ApiHelper(apiService)
        repository = Repository(apiHelper, dbHelper, userPreferences)
    }

    @Test
    fun login(): Unit = runBlocking {
        val responseLogin = mockk<User>()

        every {
            runBlocking {
                dbHelper.getUser("asd","asd")
            }
        }returns responseLogin

        repository.login("asd","asd")
        verify {
            runBlocking {
                dbHelper.getUser("asd","asd")
            }
        }
    }

    @Test
    fun getNews(): Unit = runBlocking {
        val responseNews = mockk<Response<GetAllNews>>()

        every {
            runBlocking {
                apiService.getAllNews("id", "de0e45bbc3fd4286b6d2cf8120c756ea")
            }
        } returns responseNews

        repository.getNews("id","de0e45bbc3fd4286b6d2cf8120c756ea")

        verify {
            runBlocking {
                apiService.getAllNews("id","de0e45bbc3fd4286b6d2cf8120c756ea")
            }
        }
    }
}