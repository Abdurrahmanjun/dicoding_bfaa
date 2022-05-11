package com.example.submission01.ui.feature.userdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission01.domain.model.User

class FollowingViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    lateinit var user : User

    companion object{
        private const val TAG = "FollowingViewModel"
    }
}