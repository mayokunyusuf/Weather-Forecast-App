package com.tombat.weatherforecastapp.dashboard.domain.usecase

import com.tombat.weatherforecastapp.dashboard.data.datasource.repository.WeatherRepository
import com.tombat.weatherforecastapp.dashboard.data.model.response.getWeatherResponse.GetWeatherResponseBody

class GetWeatherUseCase(private val weatherRepository: WeatherRepository) {
    suspend operator fun invoke(latitude: Double, longitude: Double): GetWeatherResponseBody {
        return weatherRepository.getWeather(latitude, longitude)
    }
}
