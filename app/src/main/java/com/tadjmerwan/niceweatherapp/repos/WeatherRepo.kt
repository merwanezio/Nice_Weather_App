package com.tadjmerwan.niceweatherapp.repos

import com.tadjmerwan.niceweatherapp.models.MeasurementUnits
import com.tadjmerwan.niceweatherapp.models.db.Forecast
import com.tadjmerwan.niceweatherapp.utils.CallResult
import kotlinx.coroutines.flow.Flow


interface WeatherRepo {

    suspend fun fetchCurrentWeather(lat: Double, lon: Double): CallResult<Boolean, Exception>

    fun getForecast(): Flow<List<Forecast>>

    fun getUnits(): Flow<MeasurementUnits>

    suspend fun setUnits(it: MeasurementUnits)

}