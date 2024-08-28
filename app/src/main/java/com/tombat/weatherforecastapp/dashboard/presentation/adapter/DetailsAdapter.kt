package com.tombat.weatherforecastapp.dashboard.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tombat.weatherforecastapp.R
import com.tombat.weatherforecastapp.dashboard.data.model.response.getWeatherResponse.Daily
import com.tombat.weatherforecastapp.dashboard.data.model.response.getWeatherResponse.GetWeatherResponseBody
import com.tombat.weatherforecastapp.databinding.TempItemBinding

class WeatherAdapter(private val context: Context) : RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    private var weatherList: List<Daily> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = TempItemBinding.inflate(inflater, parent, false)
        return WeatherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(weatherList[position])
    }

    override fun getItemCount(): Int = weatherList.size

    fun updateData(newWeatherList: List<Daily>) {
        weatherList = newWeatherList
        notifyDataSetChanged()
    }

    class WeatherViewHolder(private val binding: TempItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(weather: Daily) {
            binding.apply {
                val temperatureCelsius = weather.temp.day - 273.15
                tvTemperature.text = "${String.format("%.1f", temperatureCelsius)}°C"
                tvNow.text = weather.weather[0].main
                windSpeed.text = weather.humidity.toString()
                humidity.text = String.format("%.1f", weather.wind_speed)
                when (weather.weather[0].main) {
                    "Clouds" -> {
                        binding.weatherIcon.setImageResource(R.drawable.splashicon)
                    }
                    "Rain" -> {
                        binding.weatherIcon.setImageResource(R.drawable.showers)
                    }
                    else -> {
                        binding.weatherIcon.setImageResource(R.drawable.fastwind)
                    }
                }
            }
        }
    }
}
