package com.tombat.weatherforecastapp.dashboard.data.datasource.repository

import com.tombat.weatherforecastapp.dashboard.data.model.response.getGeocodingResponse.GetGeocodingResponseBodyItem
import com.tombat.weatherforecastapp.dashboard.data.model.response.getWeatherResponse.GetWeatherResponseBody

interface WeatherRepository {
    suspend fun getWeather(latitude: Double, longitude: Double): GetWeatherResponseBody
    suspend fun getGeoCode(q: String, limit: Int = 1): GetGeocodingResponseBodyItem
}
