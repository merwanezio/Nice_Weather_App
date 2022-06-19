package com.tadjmerwan.niceweatherapp.models.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tadjmerwan.niceweatherapp.models.response.Alert
import com.tadjmerwan.niceweatherapp.models.response.CurrentWeather
import com.tadjmerwan.niceweatherapp.models.response.DayForecast
import com.tadjmerwan.niceweatherapp.models.response.WeatherResponse


@Entity(tableName = "city_forecasts")
data class Forecast(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
//    @ColumnInfo(name = "city_name") val cityName: String,
    val latitude: Float,
    val longitude: Float,
    val timezone: String,
    @ColumnInfo(name = "timezone_offset") val timezoneOffset: Int,
    @ColumnInfo(name = "current_weather") val currentWeather: CurrentWeather,
    val daily: List<DayForecast>,
    val alerts: List<Alert>,
) {
    constructor(cwr: WeatherResponse) : this(
        0,
//        cityName,
        latitude = cwr.latitude ?: 0.0f,
        longitude = cwr.longitude ?: 0.0f,
        timezone = cwr.timezone ?: "",
        timezoneOffset = cwr.timezoneOffset ?: 0,
        currentWeather = cwr.currentWeather,
        daily = cwr.daily ?: listOf(),
        alerts = cwr.alerts ?: listOf(),
    )

    constructor() : this(
        0,
        0f,
        0f,
        "",
        0,
        CurrentWeather(),
        listOf<DayForecast>(),
        listOf<Alert>()
    )
}
