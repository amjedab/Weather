package com.sample.weather.di

import android.content.Context
import com.sample.weather.data.repo.GeoCodeApi
import com.sample.weather.data.network.GeoCodeRequestInterceptor
import com.sample.weather.data.repo.WeatherApi
import com.sample.weather.data.network.WeatherRequestInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

  @Provides
  fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor, @ApplicationContext context: Context): OkHttpClient {
    return OkHttpClient.Builder()
      .addInterceptor(loggingInterceptor)
      .addInterceptor(WeatherRequestInterceptor(context))
      .build()
  }

  @Provides
  fun provideLoggingInterceptor(): HttpLoggingInterceptor {
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.level =
      HttpLoggingInterceptor.Level.BODY
    return loggingInterceptor
  }

  @Provides
  @GeoCodeOkHttpClient
  fun provideGeoCodeOkHttpClient(loggingInterceptor: HttpLoggingInterceptor, @ApplicationContext context: Context): OkHttpClient {
    return OkHttpClient.Builder()
      .addInterceptor(loggingInterceptor)
      .addInterceptor(GeoCodeRequestInterceptor(context))
      .build()
  }

  @Provides
  @Singleton
  fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
    Retrofit.Builder()
      .baseUrl(WEATHER_BASE_URL)
      .client(okHttpClient)
      .addConverterFactory(GsonConverterFactory.create())
      .build()


  @Provides
  @Singleton
  @GeoCodeRetrofit
  fun provideGeoCodeRetrofit(@GeoCodeOkHttpClient okHttpClient: OkHttpClient): Retrofit =
    Retrofit.Builder()
      .baseUrl(GEO_CODE_BASE_URL)
      .client(okHttpClient)
      .addConverterFactory(GsonConverterFactory.create())
      .build()

  @Provides
  fun provideWeatherApi(retrofit: Retrofit): WeatherApi {
    return retrofit.create(WeatherApi::class.java)
  }

  @Provides
  fun provideGeoCodeApi(@GeoCodeRetrofit retrofit: Retrofit): GeoCodeApi {
    return retrofit.create(GeoCodeApi::class.java)
  }

  companion object {
    const val WEATHER_BASE_URL = "https://api.openweathermap.org/"
    const val GEO_CODE_BASE_URL = "https://maps.googleapis.com/"
  }

}

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class GeoCodeRetrofit

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class GeoCodeOkHttpClient