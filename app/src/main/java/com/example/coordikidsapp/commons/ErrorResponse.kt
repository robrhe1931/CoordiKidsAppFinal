package com.example.coordikidsapp.commons

// Adding ErrorResponse
data class ErrorResponse(
    var message: String?,
    var exception: Exception? = null
)
