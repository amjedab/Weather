package com.sample.weather.data.repo

import com.sample.weather.data.model.GeoCodeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GeoCodeApi {

  @GET("maps/api/geocode/json")
  suspend fun getCoordinates(
    @Query("address") address: String,
   ): Response<GeoCodeResponse>
}