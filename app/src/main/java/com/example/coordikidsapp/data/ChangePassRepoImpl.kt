package com.example.coordikidsapp.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.coordikidsapp.commons.Constants
import com.example.coordikidsapp.domain.repository.ChangePassRepo
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChangePassRepoImpl : ChangePassRepo {

    private var auth: FirebaseAuth? = null

    private val changePassStatusLiveData = MutableLiveData<Boolean>()

    init {
        auth = FirebaseAuth.getInstance()
    }

    override fun changePassword(email: String) {
        CoroutineScope(Dispatchers.IO).launch {
            auth?.sendPasswordResetEmail(email)
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        changePassStatusLiveData.postValue(true)
                        Log.d(Constants.AUTH_FORGOT_PASSWORD_TAG, "Email sent.")
                    } else {
                        changePassStatusLiveData.postValue(false)
                        Log.e(
                            Constants.AUTH_FORGOT_PASSWORD_TAG,
                            "Change password error : Something went wrong"
                        )
                    }
                }
        }
    }

    override fun getChangePassStatusLiveData(): MutableLiveData<Boolean> {
        return changePassStatusLiveData
    }
}