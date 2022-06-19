package com.tadjmerwan.niceweatherapp.data.remote

import com.tadjmerwan.niceweatherapp.models.response.GeoLocationResponse
import com.tadjmerwan.niceweatherapp.models.response.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface OpenWeatherAPI {


    /**
     * Current and forecast weather data.
     */
    @GET("data/3.0/onecall?")
    suspend fun getCurrentWeather(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("exclude") exclude: String? = null,
        @Query("appid") appid: String,
        @Query("units") units: String? = null,
        @Query("lang") lang: String? = null,
    ): Response<WeatherResponse?>


    /**
     *  Direct geocoding allows to get geographical coordinates (lat, lon) by using name of the location (city name or area name).
     */
    @GET("geo/1.0/direct?")
    suspend fun getGeoInfo(
        @Query("q") cityName: String,
        @Query("limit") limit: Int,
        @Query("appid") appid: String,
    ): Response<List<GeoLocationResponse?>?>


    /**
     *  Reverse geocoding allows to get name of the location (city name or area name) by using geografical coordinates (lat, lon).
     */
    @GET("geo/1.0/reverse?")
    suspend fun getAreaInfo(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("limit") limit: Int,
        @Query("appid") appid: String,
    ): Response<List<GeoLocationResponse?>?>

}