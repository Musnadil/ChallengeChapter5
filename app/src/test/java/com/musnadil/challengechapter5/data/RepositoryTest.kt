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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking

import org.junit.Before
import org.junit.Test
import retrofit2.Response

class RepositoryTest {

    //collaborator
    private lateinit var apiService: ApiService
    private lateinit var apiHelper: ApiHelper
    private lateinit var dbHelper: DbHelper
    private lateinit var userPreferences: UserPreferences
    private lateinit var userDao: UserDao

    //system under test
    private lateinit var repository: Repository

    val user = User(
        null,
        "admin",
        "admin",
        "admin",
    )

    @Before
    fun setUp() {
        apiService = mockk()
        userDao = mockk()
        dbHelper = DbHelper(userDao)
        userPreferences = mockk()
        apiHelper = ApiHelper(apiService)
        repository = Repository(apiHelper, dbHelper, userPreferences)
    }

    // room
    @Test
    fun register():Unit = runBlocking {
        val responseRegister = 1L

        every {
            runBlocking {
                dbHelper.addUser(user)
            }
        } returns responseRegister

        repository.register(user)

        verify {
            runBlocking {
                dbHelper.addUser(user)
            }
        }
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
    fun update():Unit = runBlocking {
        val responseLogin = 1
        every {
            runBlocking {
                dbHelper.updateUser(user)
            }
        }returns responseLogin

        repository.update(user)

        verify {
            runBlocking {
                dbHelper.updateUser(user)
            }
        }
    }

    //Data Store
    @Test
    fun saveToPref():Unit = runBlocking {
        val responseSaveUser = mockk<Unit>()
        every {
            runBlocking {
                userPreferences.saveUserToPref(user)
            }
        } returns responseSaveUser

        repository.saveToPref(user)

        verify {
            runBlocking {
                userPreferences.saveUserToPref(user)
            }
        }
    }

    @Test
    fun getUserPref():Unit = runBlocking {
        val responseGetUser = mockk<Flow<User>>()
        every {
            runBlocking {
                userPreferences.getUserFromPref()
            }
        }returns responseGetUser

        repository.getUserPref()

        verify {
            runBlocking {
                userPreferences.getUserFromPref()
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