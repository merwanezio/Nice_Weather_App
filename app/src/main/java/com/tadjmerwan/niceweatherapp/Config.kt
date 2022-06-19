package com.tadjmerwan.niceweatherapp

import android.app.Application
import android.content.pm.PackageManager
import com.tadjmerwan.niceweatherapp.models.MeasurementUnits

object Config {
    const val BASE_URL = "https://api.openweathermap.org/"

    fun API_KEY(ac: Application) = ac.packageManager.getApplicationInfo(
        ac.packageName,
        PackageManager.GET_META_DATA
    ).metaData["keyValue"].toString()

    const val DB_NAME = "nice_weather_app_db"
    const val TIMEOUT_IN_SECONDS = 30L
    const val SEARCH_THRESHOLD = 3
    const val EXCLUDE_FROM_REQUEST = "minutely,hourly"
    val DEFAULT_UNITS = MeasurementUnits.Metric
    const val SEARCH_SUGGESTION_LIMIT = 4

    fun WEATHER_ICON_URL(icon: String): String {
        return "https://openweathermap.org/img/wn/$icon@4x.png"
    }

//    fun notificationTime():Long{
//        return LocalTime.of(6,0).(LocalDate.now(), ZoneOffset.UTC)
//    }
}