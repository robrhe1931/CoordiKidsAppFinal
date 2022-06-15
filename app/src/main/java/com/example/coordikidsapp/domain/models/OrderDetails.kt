package com.example.coordikidsapp.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrderDetails(
     val course: String,
     val quantity: Int,
     val totalPrice: String,
     val paymentCoupon : String
) : Parcelable