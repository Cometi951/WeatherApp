package com.example.currentweather.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import com.example.currentweather.model.CurrentWeatherModel
import com.example.currentweather.model.ForeCast3HoursModel
import com.example.currentweather.network.Results
import com.example.currentweather.repository.Repository
import com.example.shyama.android.database.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.DecimalFormat

class MainViewModel : ViewModel() {
    private val repository by lazy {
        Repository()
    }

    private val _currentWeather = MutableStateFlow<CurrentWeatherStates>(CurrentWeatherStates.Loading)
    val currentWeather: StateFlow<CurrentWeatherStates> = _currentWeather

    fun getCurrentWeather(lat: Double, lon: Double) {
        viewModelScope.launch {

            when (val res = repository.getCurrentWeather(lat,lon)) {
                is Results.Success -> {
                    _currentWeather.value = CurrentWeatherStates.Loaded(res.data)
                }
                is Results.Error -> {
                    _currentWeather.value = CurrentWeatherStates.Error(res.errorMsg)
                }
                is Results.Loading -> {
                    _currentWeather.value = CurrentWeatherStates.Loading
                }
            }
        }
    }

    private val _Forecast3Hours= MutableStateFlow<Forecast3HoursStates>(Forecast3HoursStates.Loading)
    val forecast3Hours: StateFlow<Forecast3HoursStates> = _Forecast3Hours

    fun get3HoursForecast(lat: Double, lon: Double) {
        viewModelScope.launch {

            when (val res = repository.get3HoursForecast(lat,lon)) {
                is Results.Success -> {
                    _Forecast3Hours.value = Forecast3HoursStates.Loaded(res.data)
                }
                is Results.Error -> {
                    _Forecast3Hours.value = Forecast3HoursStates.Error(res.errorMsg)
                }
                is Results.Loading -> {
                    _Forecast3Hours.value = Forecast3HoursStates.Loading
                }
            }
        }
    }
}

sealed class CurrentWeatherStates {
    object Loading : CurrentWeatherStates()
    class Loaded (val data: CurrentWeatherModel) : CurrentWeatherStates()
    class Error(val message: String) : CurrentWeatherStates()
}

sealed class Forecast3HoursStates {
    object Loading : Forecast3HoursStates()
    class Loaded (val data: ForeCast3HoursModel) : Forecast3HoursStates()
    class Error(val message: String) : Forecast3HoursStates()
}
