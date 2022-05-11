package com.example.submission01.ui.feature.dashboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission01.data.local.UsersSource
import com.example.submission01.data.network.ApiConfig
import com.example.submission01.data.network.response.UserSearchResponse
import com.example.submission01.domain.model.User
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.InputStream

class DashboardViewModel : ViewModel() {

    private val _listUsers = MutableLiveData<List<User>>()
    val listUsers: LiveData<List<User>> = _listUsers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    var users = mutableListOf<User>()

    companion object{
        private const val TAG = "DashboardViewModel"
    }

    fun parseJSON(inputStream: InputStream): MutableList<User> {
        users = Gson().fromJson(readJSONFromAsset(inputStream), UsersSource::class.java).users.map {
            User(username = it.username, avatar = it.avatar, name = it.name, company = it.company,
                location = it.location, repository = it.repository, follower = it.follower,
                following = it.following)
        }.toMutableList()
        return users
    }

    private fun readJSONFromAsset(inputStream: InputStream): String? {
        val json: String?
        try {
            json = inputStream.bufferedReader().use{it.readText()}
        } catch (ex: Exception) {
            ex.printStackTrace()
            return null
        }
        return json
    }

    fun getUsersFromAPi(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUsers(query)
        client.enqueue(object : Callback<UserSearchResponse> {
            override fun onResponse(
                call: Call<UserSearchResponse>,
                searchItemResponse: Response<UserSearchResponse>
            ) {
                _isLoading.value = false
                if (searchItemResponse.isSuccessful) {
                    _listUsers.value = searchItemResponse.body()?.items?.map {
                        User(id = it.id, username = it.login, avatar = it.avatarUrl)
                    }
                } else {
                    Log.e(TAG, "onFailure: ${searchItemResponse.message()}")
                }
            }

            override fun onFailure(call: Call<UserSearchResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

}