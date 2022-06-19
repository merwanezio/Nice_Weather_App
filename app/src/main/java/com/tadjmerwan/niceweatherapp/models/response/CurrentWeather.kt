package com.tadjmerwan.niceweatherapp.models.response

import com.google.gson.annotations.SerializedName

data class CurrentWeather(
    @SerializedName("dt") val currentTime: Long?,
    @SerializedName("sunrise") val sunriseTime: Long?,
    @SerializedName("sunset") val sunsetTime: Long?,
    @SerializedName("temp") val temperature: Float?,
    @SerializedName("feels_like") val feelsLikeTemp: Float?,
    @SerializedName("pressure") val pressureHpa: Float?,
    @SerializedName("humidity") val humidityPercentage: Float?,
    @SerializedName("dew_point") val dewPoint: Float?,
    @SerializedName("uvi") val uvIndex: Float?,
    @SerializedName("clouds") val cloudsPercentage: Float?,
    @SerializedName("visibility") val visibilityInMeters: Float?, //max 10Km
    @SerializedName("wind_speed") val windSpeed: Float?, //metre/sec
    @SerializedName("wind_gust") val windGust: Float?,
    @SerializedName("wind_deg") val windDegree: Float?,
    @SerializedName("rain") val rain: Volume?,
    @SerializedName("snow") val snow: Volume?,
    @SerializedName("weather") val weather: List<Weather>?,
) {
    constructor() : this(
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null
    )
}
