package com.tadjmerwan.niceweatherapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.tadjmerwan.niceweatherapp.models.MeasurementUnits
import com.tadjmerwan.niceweatherapp.models.db.Forecast
import com.tadjmerwan.niceweatherapp.models.response.GeoLocationResponse
import kotlinx.coroutines.flow.Flow

@Composable
fun MainContent(
    modifier: Modifier = Modifier,
    error: String,
    forecast: Flow<List<Forecast>>,
    geoInfo: GeoLocationResponse?,
    units: MeasurementUnits,
    suggestions: State<List<GeoLocationResponse?>>,
    fetchSuggestion: (String) -> Unit,
    loadNewCallback: (GeoLocationResponse) -> Unit,
    refreshCallback: () -> Unit
) {
    if (error.isNullOrBlank()) {
        val data = forecast.collectAsState(initial = listOf())

        if (data.value.isEmpty() || geoInfo == null)
            LoadingScreen()
        else
            WeatherView(
                modifier,
                data.value.last(),
                geoInfo,
                units,
                suggestions,
                fetchSuggestion,
                loadNewCallback
            )
    } else {
        ErrorScreen(modifier, text = error, refreshCallback)
    }
}




