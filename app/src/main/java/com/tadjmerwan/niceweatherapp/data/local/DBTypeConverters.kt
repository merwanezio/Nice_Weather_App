package com.tadjmerwan.niceweatherapp.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tadjmerwan.niceweatherapp.models.response.Alert
import com.tadjmerwan.niceweatherapp.models.response.CurrentWeather
import com.tadjmerwan.niceweatherapp.models.response.DayForecast


class DBTypeConverters {

    @TypeConverter
    fun fromCurrentWeather(value: CurrentWeather): String {
        val gson = Gson()
        val type = object : TypeToken<CurrentWeather>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toCurrentWeather(value: String): CurrentWeather {
        val gson = Gson()
        val type = object : TypeToken<CurrentWeather>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromDayForecastList(value: List<DayForecast>): String {
        val gson = Gson()
        val type = object : TypeToken<List<DayForecast>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toDayForecastList(value: String): List<DayForecast> {
        val gson = Gson()
        val type = object : TypeToken<List<DayForecast>>() {}.type
        return gson.fromJson(value, type)
    }


    @TypeConverter
    fun fromAlertsList(value: List<Alert>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Alert>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toAlertsList(value: String): List<Alert> {
        val gson = Gson()
        val type = object : TypeToken<List<Alert>>() {}.type
        return gson.fromJson(value, type)
    }
}
