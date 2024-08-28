package com.tombat.weatherforecastapp.dashboard.data.datasource.repository

import android.util.Log
import com.tombat.weatherforecastapp.Constants
import com.tombat.weatherforecastapp.dashboard.data.datasource.dao.WeatherDao
import com.tombat.weatherforecastapp.dashboard.data.datasource.remote.WeatherAPIService
import com.tombat.weatherforecastapp.dashboard.data.model.response.getGeocodingResponse.GetGeocodingResponseBodyItem
import com.tombat.weatherforecastapp.dashboard.data.model.response.getWeatherResponse.GetWeatherResponseBody

class WeatherRepositoryImpl(
    private val weatherDao: WeatherDao,
    private val apiService: WeatherAPIService,
) : WeatherRepository {

    override suspend fun getWeather(latitude: Double, longitude: Double): GetWeatherResponseBody {
        return try {
            val response = apiService.getWeatherData(latitude, longitude, Constants.API_KEY)
            if (response.isSuccessful && response.body() != null) {
                val weatherData = response.body()!!

                val weatherEntities = listOf(weatherData)
                weatherDao.insertWeather(weatherEntities)
                weatherData
            } else {
                val weatherFromDb = weatherDao.getWeatherByCoordinates(latitude, longitude)
                if (weatherFromDb.isNotEmpty()) {
                    weatherFromDb.first()
                } else {
                    throw Exception("No weather data available")
                }
            }
        } catch (e: Exception) {
            Log.e("Repository", "Failed to fetch weather data: ", e)
            val weatherFromDb = weatherDao.getWeatherByCoordinates(latitude, longitude)
            if (weatherFromDb.isNotEmpty()) {
                weatherFromDb.first()
            } else {
                throw Exception("No weather data available")
            }
        }
    }

    override suspend fun getGeoCode(q: String, limit: Int): GetGeocodingResponseBodyItem {
        val response = apiService.getCoordinates(q, limit, Constants.API_KEY)
        if (response.isSuccessful && response.body() != null && response.body()!!.isNotEmpty()) {
            return response.body()!![0]
        } else {
            throw Exception("Failed to load geocoding data: ${response.message()}")
        }
    }
}