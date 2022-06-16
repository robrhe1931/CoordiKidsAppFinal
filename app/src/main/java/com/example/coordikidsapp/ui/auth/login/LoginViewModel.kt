package com.example.coordikidsapp.ui.auth.login

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.coordikidsapp.data.AuthRepoImpl
import com.example.coordikidsapp.domain.repository.AuthRepo
import com.google.firebase.auth.AuthCredential

// Setting Login View Model
class LoginViewModel(private val sharedPreferences: SharedPreferences) : ViewModel() {
    val loginStatusLiveData: LiveData<Boolean>
    private val authRepo: AuthRepo = AuthRepoImpl(sharedPreferences)

    init {
        loginStatusLiveData = authRepo.getLoginStatusLiveData()
    }

    fun regularLogin(email: String, password: String) {
        authRepo.regularLogin(email, password)
    }

    fun firebaseAuthWithGoogle(credential: AuthCredential) {
        authRepo.firebaseAuthWithGoogle(credential)
    }

}