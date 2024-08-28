package com.tombat.weatherforecastapp.dashboard.di

import android.content.Context
import androidx.room.Room
import com.tombat.weatherforecastapp.dashboard.data.datasource.dao.WeatherDao
import com.tombat.weatherforecastapp.dashboard.data.datasource.db.AppDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single { provideDatabase(androidApplication()) }
    single { provideWeatherDao(get()) }
}

fun provideDatabase(context: Context): AppDatabase {
    return Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "weather_database"
    ).build()
}

fun provideWeatherDao(database: AppDatabase): WeatherDao {
    return database.weatherDao()
}
