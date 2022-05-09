package com.example.submission01.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User (
    val id : Int = 0,
    val username : String = "",
    val name : String = "",
    val avatar : String = "",
    val company : String = "",
    val location : String = "",
    val repository : Int = 0,
    val follower : Int = 0,
    val following : Int = 0
) : Parcelable