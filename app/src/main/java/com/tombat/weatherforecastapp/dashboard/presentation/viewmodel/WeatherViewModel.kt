package com.tombat.weatherforecastapp.dashboard.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tombat.weatherforecastapp.dashboard.data.datasource.dao.WeatherDao
import com.tombat.weatherforecastapp.dashboard.data.model.response.getGeocodingResponse.GetGeocodingResponseBodyItem
import com.tombat.weatherforecastapp.dashboard.data.model.response.getWeatherResponse.GetWeatherResponseBody
import com.tombat.weatherforecastapp.dashboard.domain.usecase.GetGeoCodeUsecase
import com.tombat.weatherforecastapp.dashboard.domain.usecase.GetWeatherUseCase
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val getGeoCodeUsecase: GetGeoCodeUsecase,
    private val weatherDao: WeatherDao
) : ViewModel() {

    private val _weatherData = MutableLiveData<GetWeatherResponseBody>()
    val weatherData: LiveData<GetWeatherResponseBody> = _weatherData

    private val _allWeatherData = MutableLiveData<List<GetWeatherResponseBody>>()
    val allWeatherData: LiveData<List<GetWeatherResponseBody>> = _allWeatherData

    private val _geoocdeData = MutableLiveData<GetGeocodingResponseBodyItem>()
    val geoocdeData: LiveData<GetGeocodingResponseBodyItem> = _geoocdeData

    private val _errorMessages = MutableLiveData<String>()
    val errorMessages: LiveData<String> = _errorMessages

    fun loadWeather(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            try {
                val data = getWeatherUseCase(latitude, longitude)
                _weatherData.postValue(data)
            } catch (e: Exception) {
                _errorMessages.postValue(e.message ?: "Unknown error")
            }
        }
    }

    fun getGeocode(q: String, limit: Int) {
        viewModelScope.launch {
            try {
                val data = getGeoCodeUsecase(q, limit)
                _geoocdeData.postValue(data)
            } catch (e: Exception) {
                _errorMessages.postValue(e.message ?: "Unknown error")
            }
        }
    }

    fun getAllWeather() {
        viewModelScope.launch {
            try {
                val weatherData = weatherDao.getAllWeather()
                _allWeatherData.postValue(weatherData)
            } catch (e: Exception) {
                _errorMessages.postValue(e.message ?: "Unknown error")
            }
        }
    }
}