package com.tombat.weatherforecastapp.dashboard.di

import com.tombat.weatherforecastapp.dashboard.data.datasource.remote.WeatherAPIService
import com.tombat.weatherforecastapp.dashboard.data.datasource.repository.WeatherRepository
import com.tombat.weatherforecastapp.dashboard.data.datasource.repository.WeatherRepositoryImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val repoModule = module {
    single<WeatherAPIService> {
        get<Retrofit>().create(WeatherAPIService::class.java)
    }
    single<WeatherRepository> {
        WeatherRepositoryImpl(apiService = get(), weatherDao = get())
    }
}
