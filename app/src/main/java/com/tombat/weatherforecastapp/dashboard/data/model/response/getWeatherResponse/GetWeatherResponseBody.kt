package com.tombat.weatherforecastapp.dashboard.data.model.response.getWeatherResponse

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "weather")
data class GetWeatherResponseBody(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val current: Current,
    val daily: List<Daily>,
    val hourly: List<Hourly>,
    val lat: Double,
    val lon: Double,
    val minutely: List<Minutely>,
    val timezone: String,
    val timezone_offset: Int
)

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun fromCurrentToJson(value: Current): String = gson.toJson(value)

    @TypeConverter
    fun fromJsonToCurrent(value: String): Current = gson.fromJson(value, Current::class.java)

    @TypeConverter
    fun fromDailyListToJson(value: List<Daily>): String = gson.toJson(value)

    @TypeConverter
    fun fromJsonToDailyList(value: String): List<Daily> = gson.fromJson(value, object : TypeToken<List<Daily>>() {}.type)

    @TypeConverter
    fun fromHourlyListToJson(value: List<Hourly>): String = gson.toJson(value)

    @TypeConverter
    fun fromJsonToHourlyList(value: String): List<Hourly> = gson.fromJson(value, object : TypeToken<List<Hourly>>() {}.type)

    @TypeConverter
    fun fromMinutelyListToJson(value: List<Minutely>?): String {
        return value?.let { Gson().toJson(it) } ?: "[]"
    }

    @TypeConverter
    fun fromJsonToMinutelyList(value: String): List<Minutely> {
        return Gson().fromJson(value, object : TypeToken<List<Minutely>>() {}.type)
    }
}
