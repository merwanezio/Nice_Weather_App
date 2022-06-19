package com.tadjmerwan.niceweatherapp.repos

import com.tadjmerwan.niceweatherapp.Config
import com.tadjmerwan.niceweatherapp.data.local.AppPreferences
import com.tadjmerwan.niceweatherapp.data.local.ForecastDAO
import com.tadjmerwan.niceweatherapp.data.remote.OpenWeatherAPI
import com.tadjmerwan.niceweatherapp.models.MeasurementUnits
import com.tadjmerwan.niceweatherapp.models.db.Forecast
import com.tadjmerwan.niceweatherapp.utils.CallResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class WeatherRepoImpl(
    private val api: OpenWeatherAPI,
    private val forecastDAO: ForecastDAO,
    private val appPreferences: AppPreferences,
    private val appId: String,
) : WeatherRepo {


    override suspend fun fetchCurrentWeather(
        lat: Double,
        lon: Double
    ): CallResult<Boolean, Exception> {
        try {
            val response = api.getCurrentWeather(
                lat = lat.toString(),
                lon = lon.toString(),
                appid = appId,
                exclude = Config.EXCLUDE_FROM_REQUEST,
                units = appPreferences.getUnits.first().name.lowercase()
            )

            if (response.isSuccessful) {
                response.body()?.let {
                    forecastDAO.insert(Forecast(it))
                    return CallResult.success(true)
                }
            } else
                return CallResult.error(Exception(response.errorBody().toString()))

        } catch (e: Exception) {
            e.printStackTrace()
            return CallResult.error(e)
        }
        return CallResult.error(NullPointerException("Response Body was Null"))
    }

    override fun getForecast(): Flow<List<Forecast>> {
        return forecastDAO.getAll()
    }

    override fun getUnits(): Flow<MeasurementUnits> {
        return appPreferences.getUnits
    }

    override suspend fun setUnits(it: MeasurementUnits) {
        appPreferences.setUnits(it)
    }


}