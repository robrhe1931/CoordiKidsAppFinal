package com.example.coordikidsapp.ui.auth.forgot_pass

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.coordikidsapp.data.ChangePassRepoImpl
import com.example.coordikidsapp.domain.repository.ChangePassRepo

class ForgotPassViewModel : ViewModel() {

    val changePassStatusLiveData: LiveData<Boolean>
    private val changePassRepo: ChangePassRepo = ChangePassRepoImpl()

    init {
        changePassStatusLiveData = changePassRepo.getChangePassStatusLiveData()
    }

    fun changePassword(email: String) {
        changePassRepo.changePassword(email)
    }
}