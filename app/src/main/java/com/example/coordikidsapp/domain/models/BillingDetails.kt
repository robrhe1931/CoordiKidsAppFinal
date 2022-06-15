package com.example.coordikidsapp.domain.models

data class BillingDetails(
     val countryOrRegion: String,
     val houseNumber: String,
     val streetAddress: String,
     val suburb: String,
     val state: String,
     val postcode: String,
     val agreeTerms: Boolean,
     val subscribeStatus: Boolean
)