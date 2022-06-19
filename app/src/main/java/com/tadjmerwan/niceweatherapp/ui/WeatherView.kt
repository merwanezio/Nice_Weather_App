package com.tadjmerwan.niceweatherapp.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.tadjmerwan.niceweatherapp.Config
import com.tadjmerwan.niceweatherapp.models.MeasurementUnits
import com.tadjmerwan.niceweatherapp.models.db.Forecast
import com.tadjmerwan.niceweatherapp.models.getSpeedSymb
import com.tadjmerwan.niceweatherapp.models.getTempSymb
import com.tadjmerwan.niceweatherapp.models.response.DayForecast
import com.tadjmerwan.niceweatherapp.models.response.GeoLocationResponse
import com.tadjmerwan.niceweatherapp.models.response.Weather
import com.tadjmerwan.niceweatherapp.utils.getDayName
import com.tadjmerwan.niceweatherapp.utils.getTime
import compose.icons.WeatherIcons
import compose.icons.weathericons.*
import kotlin.math.roundToInt


@Composable
fun WeatherView(
    modifier: Modifier = Modifier,
    data: Forecast,
    geoInfo: GeoLocationResponse,
    units: MeasurementUnits,
    suggestions: State<List<GeoLocationResponse?>>,
    fetchSuggestion: (String) -> Unit,
    loadNewCallback: (GeoLocationResponse) -> Unit
) {
    val searchTerm = remember { mutableStateOf(TextFieldValue()) }


    Column(modifier) {
        SearchBar(
            Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
            {
                searchTerm.value = it
                if (it.text.length >= Config.SEARCH_THRESHOLD) {
                    fetchSuggestion.invoke(it.text)
                }
            },
            searchTerm = searchTerm.value,
            suggestions.value,
            loadNewCallback
        ) {
            searchTerm.value = TextFieldValue("")
        }
        GeneralInfo(
            modifier = Modifier
                .fillMaxHeight(0.4f)
                .padding(16.dp),
            cityName = geoInfo?.name ?: "",
            state = geoInfo.state ?: "",
            temp = data.currentWeather.temperature?.roundToInt().toString(),
            feelsLike = data.currentWeather.feelsLikeTemp.toString(),
            weather = data.currentWeather.weather?.firstOrNull(),
            units = units
        )
        DaysForecast(
            modifier = Modifier.padding(16.dp),
            data.daily,
            units = units
        )
        MoreInfo(
            modifier = Modifier
                .padding(16.dp),
            cloudPercentage = data.currentWeather.cloudsPercentage,
            humidityPercentage = data.currentWeather.humidityPercentage,
            visibilityInMeters = data.currentWeather.visibilityInMeters,
            windSpeed = data.currentWeather.windSpeed,
            sunriseTime = data.currentWeather.sunriseTime,
            sunsetTime = data.currentWeather.sunsetTime,
            units
        )

    }
}

@Composable
fun MoreInfo(
    modifier: Modifier,
    cloudPercentage: Float?,
    humidityPercentage: Float?,
    visibilityInMeters: Float?,
    windSpeed: Float?,
    sunriseTime: Long?,
    sunsetTime: Long?,
    units: MeasurementUnits
) {
    Row(
        modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column() {
            InfoLine(iv = WeatherIcons.Cloudy, text = " ${cloudPercentage?.toInt()} %")
            InfoLine(iv = WeatherIcons.Dust, text = " ${visibilityInMeters?.toInt()} Meters")
            InfoLine(iv = WeatherIcons.Sunrise, text = "${sunriseTime?.getTime()}")
        }
        Column {
            InfoLine(iv = WeatherIcons.Humidity, text = " ${humidityPercentage?.toInt()} %")
            InfoLine(
                iv = WeatherIcons.StrongWind,
                text = " ${windSpeed?.toInt()} ${units.getSpeedSymb()}"
            )
            InfoLine(iv = WeatherIcons.Sunset, text = "${sunsetTime?.getTime()}")
        }
    }
}

@Composable
fun InfoLine(iv: ImageVector, text: String) {
    Row(
        modifier = Modifier.padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = iv,
            contentDescription = null
        )

        Text(text = text)
    }
}

@Preview
@Composable
fun InfoLinePreview() {
    InfoLine(iv = WeatherIcons.Alien, text = "Aline aline loin")
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DaysForecast(modifier: Modifier, daily: List<DayForecast>, units: MeasurementUnits) {

    CompositionLocalProvider(
        LocalOverscrollConfiguration provides null
    ) {
        LazyRow(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(daily) { dayForecast ->
                DayCard(
                    dayForecast.currentTime?.getDayName() ?: "",
                    dayForecast.weather?.firstOrNull()?.icon ?: "",
                    dayForecast?.temperature?.max?.roundToInt() ?: 0,
                    dayForecast?.temperature?.min?.roundToInt() ?: 0
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DayCard(day: String, icon: String, max: Int, min: Int) {
    Card() {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(text = day)
            Box(
                modifier = Modifier
            ) {
                Image(
                    modifier = Modifier
                        .height(64.dp)
                        .width(64.dp)
                        .align(Alignment.Center),
                    painter = rememberAsyncImagePainter(Config.WEATHER_ICON_URL(icon)),
                    contentDescription = null,
                )
            }
            Row {
                Icon(
                    imageVector = Icons.Rounded.KeyboardArrowDown,
                    contentDescription = "Localized description",
                    tint = Color(0xFF303F9F)
                )
                Text(text = min.toString(), fontSize = 18.sp)
                Spacer(modifier = Modifier.width(16.dp))
                Icon(
                    imageVector = Icons.Rounded.KeyboardArrowUp,
                    contentDescription = "Localized description",
                    tint = Color(0xFFF57C00)
                )
                Text(text = max.toString(), fontSize = 18.sp)

            }
        }
    }
}

@Preview(heightDp = 300)
@Composable
private fun DayCardPreview() {
    DayCard(
        day = "Friday",
        "10n",
        45,
        16
    )
}

@Composable
fun GeneralInfo(
    modifier: Modifier = Modifier,
    cityName: String,
    state: String,
    temp: String,
    feelsLike: String,
    units: MeasurementUnits,
    weather: Weather?
) {
    Row(modifier.fillMaxWidth()) {
        Column(Modifier.fillMaxWidth(0.5f)) {
            Text(text = cityName, fontSize = 34.sp)
            Text(text = " $state")
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "$temp° ${units.getTempSymb()}", fontSize = 44.sp)

//                Text(text = "$feelsLike° ${units.getTempSymb()}",fontSize = 18.sp)
            }
//            Text(text = "${weather?.main} ", textAlign = TextAlign.Start)
            Text(text = "${weather?.description} ", textAlign = TextAlign.Start)
        }


        Column {

        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Image(
                modifier = Modifier
                    .height(128.dp)
                    .width(128.dp)
                    .align(Alignment.Center)
                    .clip(CircleShape)
                    .background(Color.LightGray),
                painter = rememberAsyncImagePainter(Config.WEATHER_ICON_URL(weather?.icon ?: "")),
                contentDescription = null,
            )

        }

    }
}


@Preview(heightDp = 300)
@Composable
private fun TopGeneralInfo() {
    GeneralInfo(
        cityName = "Dubai",
        state = "DU",
        temp = "43",
        feelsLike = "44",
        units = Config.DEFAULT_UNITS,
        weather = null
    )
}