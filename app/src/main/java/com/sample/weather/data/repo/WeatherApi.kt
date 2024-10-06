package com.sample.weather.data.repo

import com.sample.weather.data.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

  @GET("data/2.5/weather?units=imperial")
  suspend fun getWeather(
    @Query(LAT) lat: Double,
    @Query(LON) lon: Double): Response<WeatherResponse>

  companion object {
    const val LAT = "lat"
    const val LON = "lon"
  }

}