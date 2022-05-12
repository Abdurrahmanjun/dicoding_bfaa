package com.example.submission01.ui.feature.userdetails

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission01.data.network.ApiConfig
import com.example.submission01.data.network.response.UserItemResponse
import com.example.submission01.data.network.response.UserListResponse
import com.example.submission01.domain.model.User
import com.example.submission01.ui.feature.dashboard.DashboardViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel : ViewModel() {

    private val _listUsers = MutableLiveData<List<User>>()
    val listUsers: LiveData<List<User>> = _listUsers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    var user : User = User()

    companion object{
        private const val TAG = "FollowersViewModel"
    }

    fun getUsersFollower() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserfollowers(user.username)
        client.enqueue(object : Callback<List<UserItemResponse>> {
            override fun onResponse(
                call: Call<List<UserItemResponse>>,
                listItemResponse: Response<List<UserItemResponse>>
            ) {
                _isLoading.value = false
                if (listItemResponse.isSuccessful) {
                    _listUsers.value = listItemResponse.body()?.map {
                        User(id = it.id, username = it.login, avatar = it.avatarUrl)
                    }
                } else {
                    Log.e(TAG, "onFailure: ${listItemResponse.message()}")
                }
            }

            override fun onFailure(call: Call<List<UserItemResponse>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}