package com.example.submission01.data.local.dummy

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