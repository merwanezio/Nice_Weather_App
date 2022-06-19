package com.tadjmerwan.niceweatherapp.repos

import com.tadjmerwan.niceweatherapp.Config
import com.tadjmerwan.niceweatherapp.data.remote.OpenWeatherAPI
import com.tadjmerwan.niceweatherapp.models.response.GeoLocationResponse
import com.tadjmerwan.niceweatherapp.utils.CallResult

class GeoInfoRepoImpl(
    private val api: OpenWeatherAPI,
    private val appId: String
) : GeoInfoRepo {

    override suspend fun getAreaName(
        lat: Double,
        lon: Double
    ): CallResult<GeoLocationResponse?, Exception> {
        return try {
            val response = api.getAreaInfo(lat.toString(), lon.toString(), 1, appId)

            if (response.isSuccessful)
                CallResult.success(response.body()?.first())
            else
                CallResult.error(Exception(response.errorBody().toString()))

        } catch (e: Exception) {
            e.printStackTrace()
            CallResult.error(e)
        }
    }


    override suspend fun getSearchSuggestions(query: String): CallResult<List<GeoLocationResponse?>?, Exception> {
        return try {
            val response = api.getGeoInfo(query, Config.SEARCH_SUGGESTION_LIMIT, appId)

            if (response.isSuccessful)
                CallResult.success(response.body())
            else
                CallResult.error(Exception(response.errorBody().toString()))

        } catch (e: Exception) {
            e.printStackTrace()
            CallResult.error(e)
        }
    }


}