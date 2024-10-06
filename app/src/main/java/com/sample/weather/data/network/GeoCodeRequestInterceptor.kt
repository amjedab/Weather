package com.sample.weather.data.network

import android.content.Context
import com.sample.weather.R
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GeoCodeRequestInterceptor@Inject constructor(
  context: Context
) : Interceptor {

  private val geoCodeApiKey = context.resources.getString(R.string.geo_code_api_key)

  override fun intercept(chain: Interceptor.Chain): Response {

    val url = chain.request().url
      .newBuilder()
      .addQueryParameter(
        GEO_CODE_API_KEY_QUERY,
        geoCodeApiKey
      )
      .build()
    val request = chain.request().newBuilder().url(url).build()
    return chain.proceed(request)
  }

  companion object {
    const val GEO_CODE_API_KEY_QUERY = "key"
  }

}