package com.tombat.weatherforecastapp.dashboard.data.datasource.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tombat.weatherforecastapp.dashboard.data.datasource.dao.WeatherDao
import com.tombat.weatherforecastapp.dashboard.data.model.response.getWeatherResponse.Converters
import com.tombat.weatherforecastapp.dashboard.data.model.response.getWeatherResponse.GetWeatherResponseBody

@Database(entities = [GetWeatherResponseBody::class], version = 4)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}