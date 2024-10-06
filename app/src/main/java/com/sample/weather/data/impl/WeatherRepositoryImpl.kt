package com.sample.weather.data.impl

import com.sample.weather.data.model.GeoCodeResponse
import com.sample.weather.data.model.WeatherResponse
import com.sample.weather.data.repo.GeoCodeApi
import com.sample.weather.data.repo.WeatherApi
import com.sample.weather.data.repo.WeatherRepository
import timber.log.Timber

import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
  private val weatherApi: WeatherApi,
  private val geoCodeApi: GeoCodeApi
): WeatherRepository {

    override suspend fun getWeather(lat: Double, lon: Double): Result<WeatherResponse> {
        val response = weatherApi.getWeather(lat, lon)
        return if (response.isSuccessful && response.body() != null) {
            Timber.i("weatherApi isSuccessful")
            Result.success(response.body()!!)
        } else {
            Timber.d("weatherApi failure")
            Result.failure(Exception(response.message()))
        }
    }

    override suspend fun getLatLong(city: String): Result<GeoCodeResponse> {
        val response = geoCodeApi.getCoordinates(city)
        return if (response.isSuccessful && response.body() != null && response.body()!!.error_message.isNullOrEmpty()
        ) {
            Timber.i("geoCodeApi isSuccessful")
            Result.success(response.body()!!)
        } else {
            Timber.d("geoCodeApi failure")
            response.body()?.error_message?.let {
                Timber.d("geoCodeApi error message $it") }
            Result.failure(Exception((response.body()?.error_message + response.body()?.status) ?: response.message()))
        }
    }

}