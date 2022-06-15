package com.example.coordikidsapp.domain.repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.AuthCredential

interface AuthRepo {
    fun regularLogin(email : String , password : String)
    fun firebaseAuthWithGoogle(credential : AuthCredential)
    fun firebaseAuthWithFb(credential: AuthCredential)
    fun getLoginStatusLiveData() : MutableLiveData<Boolean>
}