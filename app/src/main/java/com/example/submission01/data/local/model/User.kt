package com.example.submission01.data.local.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class User (
    val username : String = "",
    val name : String = "",
    val avatar : String = "",
    val company : String = "",
    val location : String = "",
    val repository : Int = 0,
    val follower : Int = 0,
    val following : Int = 0
)