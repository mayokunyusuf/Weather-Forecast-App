package com.tombat.weatherforecastapp.dashboard.presentation.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.tombat.weatherforecastapp.dashboard.presentation.adapter.WeatherAdapter
import com.tombat.weatherforecastapp.dashboard.presentation.isNetworkAvailable
import com.tombat.weatherforecastapp.dashboard.presentation.viewmodel.WeatherViewModel
import com.tombat.weatherforecastapp.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val weatherAdapter: WeatherAdapter by lazy {
        WeatherAdapter(requireContext())
    }
    private val viewModel: WeatherViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        initViews()
        observeWeatherData()
    }

    private fun initViews() {
        if (requireContext().isNetworkAvailable()) {
            viewModel.loadWeather(6.45, 3.39)
        } else {
            getFromDb("Lagos")
            showError("No internet connection.")
        }

        binding.searchIcon.setOnClickListener {
            if (binding.searchCityField.visibility == View.VISIBLE) {
                binding.searchCityField.visibility = View.GONE
            } else {
                binding.searchCityField.visibility = View.VISIBLE
            }
        }

        binding.searchCity.setOnEditorActionListener { textView, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                textView.text.toString().takeIf { it.isNotEmpty() }?.let { searchQuery ->
                    fetchCoordinates(searchQuery)
                    textView.clearFocus()
                    binding.searchCity.text?.clear()
                    binding.searchCityField.visibility = View.GONE
                    val inputMethodManager = textView.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(textView.windowToken, 0)
                }
                true
            } else {
                false
            }
        }
    }

    private fun getFromDb(city : String) {
        viewModel.getWeatherByTimezone(city)
        observeWeatherData()
    }

    private fun fetchCoordinates(cityName: String) {
        if (requireContext().isNetworkAvailable()) {
            viewModel.getGeocode(cityName, 1)
            observeGeocodeData()
        } else {
            showError("No internet connection.")
            getFromDb(cityName)
        }
    }

    private fun observeGeocodeData() {
        viewModel.geoocdeData.observe(viewLifecycleOwner) { geocodeData ->
            geocodeData?.let {
                viewModel.loadWeather(it.lat, it.lon)
                observeWeatherData()
            } ?: run {
            }
        }
    }

    private fun observeWeatherData() {
        viewModel.weatherData.observe(viewLifecycleOwner) { weatherData ->
            if (weatherData != null) {
                binding.shimmerLayout.visibility = View.GONE
                binding.contentViewGroup.visibility = View.VISIBLE
                binding.location.text = weatherData.timezone.split("/").last()
                val temperatureCelsius = weatherData.daily[0].temp.day - 273.15
                binding.temp.text = "${String.format("%.1f", temperatureCelsius)}°C"
                binding.status.text = weatherData.daily[0].summary
                binding.scaleTemp.text = "Humidity: ${weatherData.daily[0].humidity}"
                weatherAdapter.updateData(weatherData.daily)
            } else {
                binding.shimmerLayout.visibility = View.VISIBLE
                showError("Weather data not available.")
            }
        }
    }

    private fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    private fun setupRecyclerView() {
        binding.weatherRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 4)
            adapter = weatherAdapter

            setPadding(16, 16, 16, 16)
            clipToPadding = false
        }
    }
}