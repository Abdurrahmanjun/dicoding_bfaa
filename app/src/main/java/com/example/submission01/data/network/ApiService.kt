package com.example.submission01.data.network

import com.example.submission01.data.network.response.UserDetailsResponse
import com.example.submission01.data.network.response.UserItemResponse
import com.example.submission01.data.network.response.UserListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getUsers(@Query("q") id: String): Call<UserListResponse>

    @GET("users/{username}")
    fun getUserDetails(@Path("username") id: String): Call<UserDetailsResponse>

    @GET("users/{username}/followers")
    fun getUserfollowers(@Path("username") id: String): Call<List<UserItemResponse>>

    @GET("users/{username}/following")
    fun getUserfollowing(@Path("username") id: String): Call<List<UserItemResponse>>
}