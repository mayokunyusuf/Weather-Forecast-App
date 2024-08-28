package com.tombat.weatherforecastapp.dashboard.di

import com.tombat.weatherforecastapp.dashboard.domain.usecase.GetGeoCodeUsecase
import com.tombat.weatherforecastapp.dashboard.domain.usecase.GetWeatherUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory {
        GetWeatherUseCase(weatherRepository = get())
    }
    factory {
        GetGeoCodeUsecase(weatherRepository = get())
    }
}
