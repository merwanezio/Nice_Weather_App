package com.tadjmerwan.niceweatherapp.repos

import com.tadjmerwan.niceweatherapp.models.response.GeoLocationResponse
import com.tadjmerwan.niceweatherapp.utils.CallResult

interface GeoInfoRepo {

    suspend fun getAreaName(lat: Double, lon: Double): CallResult<GeoLocationResponse?, Exception>

    suspend fun getSearchSuggestions(query: String): CallResult<List<GeoLocationResponse?>?, Exception>

}