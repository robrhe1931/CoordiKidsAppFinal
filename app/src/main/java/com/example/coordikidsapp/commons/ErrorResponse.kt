package com.example.coordikidsapp.commons

data class ErrorResponse(
    var message: String?,
    var exception: Exception? = null
)
