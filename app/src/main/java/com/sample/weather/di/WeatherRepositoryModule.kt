package com.sample.weather.di

import com.sample.weather.data.repo.WeatherRepository
import com.sample.weather.data.impl.WeatherRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WeatherRepositoryModule {

    @Provides
    @Singleton
    fun provideWeatherRepository(impl: WeatherRepositoryImpl): WeatherRepository {
        return impl
    }

}