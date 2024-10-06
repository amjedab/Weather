package com.sample.weather.data.network


import android.content.Context
import com.sample.weather.R
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRequestInterceptor@Inject constructor(
  context: Context
) : Interceptor {
  private val openWeatherApiKey = context.resources.getString(R.string.open_weather_api_key)

  override fun intercept(chain: Interceptor.Chain): Response {
    val url = chain.request().url
      .newBuilder()
      .addQueryParameter(
        API_KEY_QUERY,
        openWeatherApiKey
      )
      .build()
    val request = chain.request().newBuilder().url(url).build()
    return chain.proceed(request)
  }

  companion object {
    const val API_KEY_QUERY = "APPID"
  }

}