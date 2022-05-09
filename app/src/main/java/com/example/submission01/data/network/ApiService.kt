package com.example.submission01.data.network

import com.example.submission01.data.network.response.UserSearchItemResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("search/users?q={username}}")
    fun getUsers(@Path("username") id: String): Call<List<UserSearchItemResponse>>

    @GET("users/{username}")
    fun getUserDetails(@Path("username") id: String): Call<UserSearchItemResponse>

    @GET("users/{username}/followers")
    fun getUserfollowers(@Path("username") id: String): Call<UserSearchItemResponse>

    @GET("users/{username}")
    fun getUserfollowing(@Path("username") id: String): Call<UserSearchItemResponse>
}