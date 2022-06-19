package com.tadjmerwan.niceweatherapp.ui

import android.location.Location
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tadjmerwan.niceweatherapp.Config
import com.tadjmerwan.niceweatherapp.models.MeasurementUnits
import com.tadjmerwan.niceweatherapp.models.response.GeoLocationResponse
import com.tadjmerwan.niceweatherapp.repos.GeoInfoRepo
import com.tadjmerwan.niceweatherapp.repos.WeatherRepo
import com.tadjmerwan.niceweatherapp.utils.onError
import com.tadjmerwan.niceweatherapp.utils.success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.concurrent.CancellationException
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val weatherRepo: WeatherRepo,
    private val geoInfoRepo: GeoInfoRepo,
) : ViewModel() {

    private var currentLocation = Location("null")

    fun getWeatherData(geo: GeoLocationResponse) {
        val location = Location("search")
        location.latitude = geo.latitude?.toDouble() ?: 0.0
        location.longitude = geo.longitude?.toDouble() ?: 0.0
        getWeatherData(location)
    }

    fun refresh() {
        getWeatherData(currentLocation)
    }

    fun getWeatherData(location: Location) {

        currentLocation = location
        viewModelScope.launch {

            val fetchLog = weatherRepo.fetchCurrentWeather(location.latitude, location.longitude)
            val areaNameRes = geoInfoRepo.getAreaName(location.latitude, location.longitude)

            fetchLog.onError {
                _error.value = it.message ?: "Error Fetching current weather"
            }

            areaNameRes.success {
                _geoInfo.value = it
            }

            areaNameRes.onError {
                _error.value = it.message ?: "Error Fetching Geo location info"
            }
        }
        getUnits()
        _searchSuggestions.value = listOf()
    }

    private var _error = mutableStateOf<String>("")
    var error: State<String> = _error

    private var _geoInfo = mutableStateOf<GeoLocationResponse?>(null)
    var geoInfo: State<GeoLocationResponse?> = _geoInfo

    val forcast = weatherRepo.getForecast()

    fun noGpsError() {
        _error.value = "You Must enable Location toggle"
    }


    var units: MeasurementUnits = Config.DEFAULT_UNITS

    fun getUnits() {
        viewModelScope.launch {
            units = weatherRepo.getUnits().first()
        }
    }

    private var _searchSuggestions =
        mutableStateOf<List<GeoLocationResponse?>>(listOf<GeoLocationResponse?>())
    var searchSuggestions: State<List<GeoLocationResponse?>> = _searchSuggestions

    private var suggestionJob: Job? = null
    fun fetchSuggestions(query: String) {
        suggestionJob?.cancel(CancellationException("Launching Another"))

        suggestionJob = viewModelScope.launch {
            val res = geoInfoRepo.getSearchSuggestions(query)
            res.success {
                it?.let { safeIt ->
                    _searchSuggestions.value = safeIt
                }
            }
            res.onError {}
        }
    }

    fun changeUnits(it: MeasurementUnits) {
        viewModelScope.launch {
            weatherRepo.setUnits(it)
        }
    }

}
