package com.tadjmerwan.niceweatherapp.models.response

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("lat") val latitude: Float?,
    @SerializedName("lon") val longitude: Float?,
    @SerializedName("timezone") val timezone: String?,
    @SerializedName("timezone_offset") val timezoneOffset: Int?,
    @SerializedName("current") val currentWeather: CurrentWeather,
    @SerializedName("daily") val daily: List<DayForecast>?,
    @SerializedName("alerts") val alerts: List<Alert>?,
)







