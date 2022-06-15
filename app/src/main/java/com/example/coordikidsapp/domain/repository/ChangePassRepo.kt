package com.example.coordikidsapp.domain.repository

import androidx.lifecycle.MutableLiveData

interface ChangePassRepo {
    fun changePassword(email: String)
    fun getChangePassStatusLiveData() : MutableLiveData<Boolean>
}