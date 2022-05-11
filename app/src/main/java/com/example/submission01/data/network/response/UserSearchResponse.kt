package com.example.submission01.data.network.response

import com.google.gson.annotations.SerializedName

data class UserSearchResponse(
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,
    @SerializedName("items")
    val items: List<UserSearchItemResponse>,
    @SerializedName("total_count")
    val totalCount: Int
)