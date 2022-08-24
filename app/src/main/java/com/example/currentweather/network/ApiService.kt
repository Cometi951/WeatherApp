package com.example.shyama.android.database

import android.util.Log
import com.example.currentweather.Constant.API_KEY
import com.example.currentweather.Constant.BASE_URL
import com.example.currentweather.model.CurrentWeatherModel
import com.example.currentweather.model.ForeCast3HoursModel
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.*
import kotlinx.serialization.json.Json
import java.util.concurrent.CancellationException

object ApiService {

    val client: HttpClient = HttpClient() {
        // Logging
        install(Logging) {
            level = LogLevel.ALL
        }
        expectSuccess = true
        // Timeout
        install(HttpTimeout) {
            requestTimeoutMillis = 50000L
            connectTimeoutMillis = 50000L
            socketTimeoutMillis = 50000L
        }
        install(HttpRequestRetry) {
//            maxRetries = 5
            retryOnExceptionIf() { request, cause ->
                cause is CancellationException
            }
        }
        install(ContentNegotiation) {
            var converter = KotlinxSerializationConverter(Json {
                prettyPrint = true
                ignoreUnknownKeys = true
                explicitNulls = false
            })
            register(ContentType.Application.Json, converter)
        }
    }

    suspend fun getCurrentWeather(lat: Double, lon: Double): CurrentWeatherModel {
        return try {
            val httpResponse = client.get(BASE_URL + "/data/2.5/weather") {
                parameter("appid", API_KEY)
                parameter("lat", lat)
                parameter("lon", lon)
            }.body<CurrentWeatherModel>()
            httpResponse
        } catch (e: Exception) {
            Log.d("error", "get Data response: ${e.message}")
            CurrentWeatherModel()
        }
    }

    suspend fun get3HoursForecast(lat: Double, lon: Double): ForeCast3HoursModel {
        return try {
            val httpResponse = client.get(BASE_URL + "/data/2.5/forecast") {
                parameter("appid", API_KEY)
                parameter("lat", lat)
                parameter("lon", lon)
            }.body<ForeCast3HoursModel>()
            httpResponse
        } catch (e: Exception) {
            Log.d("error", "get Data response: ${e.message}")
            ForeCast3HoursModel()
        }
    }


}
