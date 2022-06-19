package com.tadjmerwan.niceweatherapp.models.response

import com.google.gson.annotations.SerializedName

data class DayForecast(
    @SerializedName("dt") val currentTime: Long?,
    @SerializedName("sunrise") val sunriseTime: Long?,
    @SerializedName("sunset") val sunsetTime: Long?,
    @SerializedName("moonrise") val moonriseTime: Long?,
    @SerializedName("moonset") val moonSetTime: Long?,
    @SerializedName("moon_phase") val moonPhase: Float?, //Moon phase. 0 and 1 are 'new moon', 0.25 is 'first quarter moon', 0.5 is 'full moon' and 0.75 is 'last quarter moon'. The periods in between are called 'waxing crescent', 'waxing gibous', 'waning gibous', and 'waning crescent', respectively.
    @SerializedName("temp") val temperature: Temperature?,

    @SerializedName("feels_like") val feelsLike: Temperature?,
    @SerializedName("pressure") val pressureHpa: Float?,
    @SerializedName("humidity") val humidityPercentage: Float?,
    @SerializedName("dew_point") val dewPoint: Float?,
    @SerializedName("wind_speed") val windSpeed: Float?,
    @SerializedName("wind_deg") val windDeg: Float?,
    @SerializedName("wind_gust") val windGust: Float?,
    @SerializedName("clouds") val cloudsPercentage: Float?,
    @SerializedName("pop") val precipitationProb: Float?,//between 0 and 1 percentage
    @SerializedName("rain") val rainVolume: Float?, //in MM
    @SerializedName("snow") val snowVolume: Float?,
    @SerializedName("uvi") val uvIndex: Float?,//max uvi of the day
    @SerializedName("weather") val weather: List<Weather>?,
)

