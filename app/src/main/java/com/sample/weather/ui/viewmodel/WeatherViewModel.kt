package com.sample.weather.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.weather.data.model.GeoCodeResponse
import com.sample.weather.data.model.WeatherResponse
import com.sample.weather.data.repo.WeatherRepository
import com.sample.weather.ui.screens.WeatherUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
  private val weatherRepository: WeatherRepository
) : ViewModel() {

  private val _weather = MutableStateFlow<WeatherResponse?>(null)
  val weather = _weather.asStateFlow()

  private val _geoCode = MutableStateFlow<GeoCodeResponse?>(null)
  val geoCode = _geoCode.asStateFlow()

  private val _weatherState = MutableStateFlow<WeatherUiState>(WeatherUiState.Idle)
  val weatherState = _weatherState.asStateFlow()

  init {
    //TODO add the call to get the stored location from shared preferences

  }

  //Returns the weather for the given lat and long
  private fun getWeather(lat: Double, lon: Double) =
    viewModelScope.launch(Dispatchers.IO) {
      try {
        val response = weatherRepository.getWeather(lat, lon)
        if (response.isSuccess && response.getOrNull() != null) {
          Timber.i("getWeather response: ${response.getOrNull()}")
          _weatherState.value = WeatherUiState.Success(response.getOrNull()!!)
        } else {
          _weatherState.value = WeatherUiState.Error("Error in getWeather: ${response.exceptionOrNull()}")
        }
      } catch (e: Exception) {
        _weatherState.value = WeatherUiState.Error("Error in getWeather: ${e.message}")
        Timber.i("Error in getWeather: ${e.message}")
      }
    }

  //Returns the geo code for the given city
  fun getGeoCode(cityName: String) =
    viewModelScope.launch(Dispatchers.IO) {
      try {
        _weatherState.value = WeatherUiState.Loading
        val response = weatherRepository.getLatLong(cityName)
        if(response.isSuccess && response.getOrNull() != null) {
          _geoCode.value = response.getOrNull()!!
          Timber.i("getGeoCode response: ${response.getOrNull()!!.results.firstOrNull()}")

          if (response.getOrNull()!!.results.firstOrNull() != null &&
            response.getOrNull()!!.results.first().geometry.location.lat != 0.0 && response.getOrNull()!!.results.first().geometry.location.lng != 0.0
          ) {
            getWeather(
              response.getOrNull()!!.results.first().geometry.location.lat,
              response.getOrNull()!!.results.first().geometry.location.lat
            )
          }
        } else {
          _weatherState.value = WeatherUiState.Error("Error in getGeoCode: ${response.exceptionOrNull()}")
        }
      } catch (e: Exception) {
        _weatherState.value = WeatherUiState.Error("Error in getGeoCode: ${e.message}")
        Timber.i("Error in getGeoCode: ${e.message}")
      }
    }
}

