package com.tombat.weatherforecastapp.dashboard.data.model.response.getWeatherResponse

data class Temp(
    val day: Double,
    val eve: Double,
    val max: Double,
    val min: Double,
    val morn: Double,
    val night: Double
)