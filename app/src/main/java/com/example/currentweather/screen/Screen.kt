package com.example.currentweather.screen

sealed class Screen(val route: String){
    object Home: Screen("Home")
    object DailyForecast: Screen("DailyForecast")
}