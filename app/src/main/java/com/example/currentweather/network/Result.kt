package com.example.currentweather.network

sealed class Results <out T : Any> {
    object Loading : Results<Nothing>()
    data class Success<out T: Any>(val data: T) : Results<T>()
    data class Error (val errorMsg: String): Results<Nothing>()
}
