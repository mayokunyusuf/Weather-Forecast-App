package com.tombat.weatherforecastapp.dashboard.domain.usecase

import com.tombat.weatherforecastapp.dashboard.data.datasource.repository.WeatherRepository
import com.tombat.weatherforecastapp.dashboard.data.model.response.getGeocodingResponse.GetGeocodingResponseBodyItem

class GetGeoCodeUsecase(private val weatherRepository: WeatherRepository) {
    suspend operator fun invoke(q: String, limit: Int): GetGeocodingResponseBodyItem {
        return weatherRepository.getGeoCode(q, limit)
    }
}
