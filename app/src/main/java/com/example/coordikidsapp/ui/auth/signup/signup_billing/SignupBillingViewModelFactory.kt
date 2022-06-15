package com.example.coordikidsapp.ui.auth.signup.signup_billing

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SignupBillingViewModelFactory(
    private val profileSharedPreferences: SharedPreferences
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SignupBillingViewModel( profileSharedPreferences) as T
    }
}