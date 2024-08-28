package com.tombat.weatherforecastapp.dashboard.data.model.response.getGeocodingResponse

data class GetGeocodingResponseBodyItem(
    val country: String,
    val lat: Double,
    val local_names: LocalNames,
    val lon: Double,
    val name: String,
    val state: String
)