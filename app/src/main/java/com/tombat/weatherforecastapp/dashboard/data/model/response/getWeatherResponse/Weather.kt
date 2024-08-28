package com.tombat.weatherforecastapp.dashboard.data.model.response.getWeatherResponse

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)