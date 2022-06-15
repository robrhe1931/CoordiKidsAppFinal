package com.example.coordikidsapp.ui.auth.signup.signup_billing

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.coordikidsapp.data.CourseOrderRepoImpl
import com.example.coordikidsapp.domain.models.CourseOrder
import com.example.coordikidsapp.domain.repository.CourseOrderRepo

class SignupBillingViewModel(
    private val profileSharedPreferences: SharedPreferences
) : ViewModel() {
    val orderStatusLiveData: LiveData<Boolean>
    private val courseOrderRepo: CourseOrderRepo = CourseOrderRepoImpl( profileSharedPreferences)

    init {
        orderStatusLiveData = courseOrderRepo.getOrderStatusLiveData()
    }

    fun createOrderRegister(courseOrder: CourseOrder) {
        courseOrderRepo.registerUser(courseOrder)
    }

    fun createOrder(courseOrder: CourseOrder){
        courseOrderRepo.createOrder(courseOrder)
    }
}