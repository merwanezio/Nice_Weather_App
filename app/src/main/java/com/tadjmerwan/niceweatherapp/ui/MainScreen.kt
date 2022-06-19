package com.tadjmerwan.niceweatherapp.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun MainScreen(
    viewModel: MainScreenViewModel = hiltViewModel(),
    locationCallback: (vm: MainScreenViewModel) -> Unit
) {
    val locationPermissionState = rememberPermissionState(
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )
    val settings = remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            MainTopBar(actionCallback = {
                settings.value = settings.value.not()
            })
        }
    ) {
        when (locationPermissionState.status) {
            PermissionStatus.Granted -> {
                LaunchedEffect(locationPermissionState.status) {
                    locationCallback.invoke(viewModel)
                }
                if (settings.value) {
                    SettingsScreen(
                        changeUnitCallback = {
                            viewModel.changeUnits(it)
                            viewModel.getUnits()
                            viewModel.refresh()
                        },
                        units = viewModel.units
                    )
                } else
                    MainContent(
                        Modifier.padding(it),
                        viewModel.error.value,
                        viewModel.forcast,
                        viewModel.geoInfo.value,
                        viewModel.units,
                        viewModel.searchSuggestions,
                        { term ->
                            viewModel.fetchSuggestions(term)
                        }, {
                            viewModel.getWeatherData(it)
                        },
                        {
                            viewModel.refresh()
                        }
                    )
            }
            is PermissionStatus.Denied -> {
                NoGpsScreen(
                    Modifier.padding(it),
                    locationPermissionState
                ) { locationPermissionState.launchPermissionRequest() }
            }
        }
    }
}
