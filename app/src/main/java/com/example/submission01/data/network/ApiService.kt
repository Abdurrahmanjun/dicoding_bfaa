package com.example.submission01.data.network

import com.example.submission01.data.network.response.UserSearchItemResponse
import com.example.submission01.data.network.response.UserSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getUsers(@Query("q") id: String): Call<UserSearchResponse>

    @GET("users/{username}")
    fun getUserDetails(@Path("username") id: String): Call<UserSearchItemResponse>

    @GET("users/{username}/followers")
    fun getUserfollowers(@Path("username") id: String): Call<UserSearchItemResponse>

    @GET("users/{username}")
    fun getUserfollowing(@Path("username") id: String): Call<UserSearchItemResponse>
}