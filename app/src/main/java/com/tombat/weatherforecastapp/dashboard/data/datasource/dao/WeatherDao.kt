package com.tombat.weatherforecastapp.dashboard.data.datasource.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.tombat.weatherforecastapp.dashboard.data.model.response.getWeatherResponse.GetWeatherResponseBody

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWeather(weather: List<GetWeatherResponseBody>)

    @Query("SELECT * FROM weather WHERE lat BETWEEN :latitude - 0.1 AND :latitude + 0.1 AND lon BETWEEN :longitude - 0.1 AND :longitude + 0.1")
    suspend fun getWeatherByCoordinates(latitude: Double, longitude: Double): List<GetWeatherResponseBody>

    @Query("SELECT * FROM weather WHERE timezone LIKE '%' || :timezone || '%'")
    suspend fun getWeatherByTimezone(timezone: String): GetWeatherResponseBody

    @Query("SELECT * FROM weather")
    suspend fun getAllWeather(): List<GetWeatherResponseBody>
}