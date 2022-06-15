package com.example.coordikidsapp.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserDetails(
     val caregiverFName: String,
     val caregiverLName: String,
     val phoneNumber: String,
     val email: String,
     var password: String,
     val childFName: String,
     val childLName: String,
     val childDOB: String
) : Parcelable