package com.example.coordikidsapp.domain.models

data class UserProfileDetails(
    val caregiverFName: String,
    val caregiverLName: String,
    val phoneNumber: String,
    val email: String,
    val childFName: String,
    val childLName: String,
    val childDOB: String
)