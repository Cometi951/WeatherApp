package com.example.currentweather.repository

import com.example.currentweather.model.CurrentWeatherModel
import com.example.currentweather.model.ForeCast3HoursModel
import com.example.currentweather.network.Results
import com.example.currentweather.network.SafeApi
import com.example.shyama.android.database.ApiService

class Repository {

    suspend fun getCurrentWeather(lat: Double, lon: Double): Results<CurrentWeatherModel> {

        return SafeApi.safeApiCall {
            ApiService.getCurrentWeather(lat,lon)
        }
    }

    suspend fun get3HoursForecast(lat: Double, lon: Double): Results<ForeCast3HoursModel> {

        return SafeApi.safeApiCall {
            ApiService.get3HoursForecast(lat,lon)
        }
    }

}

