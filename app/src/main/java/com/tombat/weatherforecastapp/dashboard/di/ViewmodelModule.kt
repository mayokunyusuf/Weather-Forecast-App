package com.tombat.weatherforecastapp.dashboard.di

import com.tombat.weatherforecastapp.dashboard.presentation.viewmodel.WeatherViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        WeatherViewModel(
            getWeatherUseCase = get(),
            getGeoCodeUsecase = get(),
            weatherDao = get()
        )
    }
}
