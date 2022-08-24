package com.example.currentweather.model

import kotlinx.serialization.Serializable

@Serializable
data class ForeCast3HoursModel(
    val city: City? = null,
    val cnt: Int? = null,
    val cod: String? = null,
    val list: List<Data?>? = null,
    val message: Int? = null
) {
    @Serializable
    data class City(
        val coord: Coord? = null,
        val country: String? = null,
        val id: Int? = null,
        val name: String? = null,
        val population: Int? = null,
        val sunrise: Int? = null,
        val sunset: Int? = null,
        val timezone: Int? = null
    ) {
        @Serializable
        data class Coord(
            val lat: Double? = null,
            val lon: Double? = null
        )
    }
    @Serializable
    data class Data(
        val clouds: Clouds? = null,
        val dt: Int? = null,
        val dt_txt: String? = null,
        val main: Main? = null,
        val pop: Double? = null,
        val rain: Rain? = null,
        val sys: Sys? = null,
        val visibility: Int? = null,
        val weather: List<Weather?>? = null,
        val wind: Wind? = null
    ) {
        @Serializable
        data class Clouds(
            val all: Int? = null
        )

        @Serializable
        data class Main(
            val feels_like: Double? = null,
            val grnd_level: Int? = null,
            val humidity: Int? = null,
            val pressure: Int? = null,
            val sea_level: Int? = null,
            val temp: Double? = null,
            val temp_kf: Double? = null,
            val temp_max: Double? = null,
            val temp_min: Double? = null
        )

        @Serializable
        data class Rain(
            val `3h`: Double? = null
        )

        @Serializable
        data class Sys(
            val pod: String? = null
        )

        @Serializable
        data class Weather(
            val description: String? = null,
            val icon: String? = null,
            val id: Int? = null,
            val main: String? = null
        )

        @Serializable
        data class Wind(
            val deg: Int? = null,
            val gust: Double? = null,
            val speed: Double? = null
        )
    }
}