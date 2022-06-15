package com.example.coordikidsapp.utils

import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavDirections

fun NavController.safeNavigate(direction: NavDirections){
    if (currentDestination != null && currentDestination!!.getAction(direction.actionId) != null) {
        navigate(direction)
    }
    else Log.d("SAFE_NAVIGATE", "Trying to navigate with an action which is not of current screen")
}