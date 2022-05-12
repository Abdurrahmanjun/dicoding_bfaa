package com.example.submission01.ui.feature.userdetails

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission01.data.network.ApiConfig
import com.example.submission01.data.network.response.UserDetailsResponse
import com.example.submission01.domain.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel : ViewModel() {

    private val _userDetail = MutableLiveData<User>()
    val userDetail: LiveData<User> = _userDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object{
        private const val TAG = "DetailUserViewModel"
    }

    fun getuserDetail(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserDetails(username)
        client.enqueue(object : Callback<UserDetailsResponse> {
            override fun onResponse(
                call: Call<UserDetailsResponse>,
                itemResponse: Response<UserDetailsResponse>
            ) {
                _isLoading.value = false
                if (itemResponse.isSuccessful) {
                    _userDetail.value = User(id = itemResponse.body()?.id ?: 0,
                        username = itemResponse.body()?.login ?: "",
                        name = itemResponse.body()?.name?: "",
                        avatar = itemResponse.body()?.avatarUrl?: "",
                        location = itemResponse.body()?.location?: "",
                        following = itemResponse.body()?.following?: 0,
                        follower = itemResponse.body()?.followers?: 0)
                } else {
                    Log.e(TAG, "onFailure: ${itemResponse.message()}")
                }
            }

            override fun onFailure(call: Call<UserDetailsResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}