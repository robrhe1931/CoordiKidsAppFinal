package com.example.coordikidsapp.data

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.coordikidsapp.commons.Constants
import com.example.coordikidsapp.domain.repository.AuthRepo
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.squareup.okhttp.Dispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class AuthRepoImpl(private val sharedPreferences: SharedPreferences) : AuthRepo {

    private var auth: FirebaseAuth? = null

    private val loginStatusLiveData = MutableLiveData<Boolean>()

    init {
        auth = FirebaseAuth.getInstance()
        if (!sharedPreferences.getBoolean("REMEMBER_STATUS", false)) {
            sharedPreferences.edit().putBoolean("LOGIN_STATUS", false).commit()
            auth?.signOut()
        }
        if (auth!!.currentUser != null) {
            loginStatusLiveData.postValue(true)
        }


    }

    override fun regularLogin(email: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            auth?.signInWithEmailAndPassword(email, password)?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(Constants.AUTH_LOGIN_TAG, "Regular login success")
                    if(auth?.currentUser!!.isEmailVerified){
                        loginStatusLiveData.postValue(true)
                    }else{
                        loginStatusLiveData.postValue(false)
                        Log.i(Constants.AUTH_LOGIN_TAG, "User email is not verified")
                    }
                } else {
                    loginStatusLiveData.postValue(false)
                    Log.e(Constants.AUTH_LOGIN_TAG, "Login error: Something went wrong")
                }
            }
        }
    }

//    override fun forgotPass(email: String) {

//    }

    override fun firebaseAuthWithGoogle(credential: AuthCredential) {
        CoroutineScope(Dispatchers.IO).launch {
            auth?.signInWithCredential(credential)
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(Constants.GOOGLE_LOGIN_TAG, "signInWithCredential:success")
                        loginStatusLiveData.postValue(true)
                    } else {
                        loginStatusLiveData.postValue(false)
                        Log.e(
                            Constants.GOOGLE_LOGIN_TAG,
                            "signInWithCredential:failure",
                            task.exception
                        )
                    }
                }
        }
    }

    override fun firebaseAuthWithFb(credential: AuthCredential) {
        CoroutineScope(Dispatchers.IO).launch {
            auth?.signInWithCredential(credential)?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(Constants.FACEBOOK_LOGIN_TAG, "signInWithCredential:success")
                    loginStatusLiveData.postValue(true)
                } else {
                    loginStatusLiveData.postValue(false)
                    Log.e(
                        Constants.FACEBOOK_LOGIN_TAG,
                        "signInWithCredential:failure",
                        task.exception
                    )
                }
            }
        }
    }

    override fun getLoginStatusLiveData(): MutableLiveData<Boolean> {
        return loginStatusLiveData
    }
}