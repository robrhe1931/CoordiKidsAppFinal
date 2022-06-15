package com.example.coordikidsapp.domain.repository

import androidx.lifecycle.MutableLiveData
import com.example.coordikidsapp.domain.models.CourseOrder

interface CourseOrderRepo {
    fun registerUser(courseOrder: CourseOrder)
    fun uploadUserProfile(courseOrder: CourseOrder)
    fun createOrder(courseOrder: CourseOrder)
    fun getOrderStatusLiveData() : MutableLiveData<Boolean>
}