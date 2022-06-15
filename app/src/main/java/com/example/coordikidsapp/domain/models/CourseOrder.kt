package com.example.coordikidsapp.domain.models


data class CourseOrder(
     val orderId : String,
     val orderDetails: OrderDetails,
     var userDetails: UserDetails,
     val billingDetails: BillingDetails
)