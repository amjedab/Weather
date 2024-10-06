package com.sample.weather.data.repo

import com.sample.weather.data.model.GeoCodeResponse
import com.sample.weather.data.model.WeatherResponse

interface WeatherRepository {

  suspend fun getWeather(lat: Double, lon: Double): Result<WeatherResponse>

  suspend fun getLatLong(city: String): GeoCodeResponse

}