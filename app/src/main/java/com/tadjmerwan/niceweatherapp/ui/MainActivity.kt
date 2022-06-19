package com.tadjmerwan.niceweatherapp.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.app.ActivityCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.tadjmerwan.niceweatherapp.AppLocationProvider
import com.tadjmerwan.niceweatherapp.ui.theme.NiceWeatherAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        val locationCallback: (vm: MainScreenViewModel) -> Unit = {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                if (isLocationEnabled())
                    getLocation(it)
                else
                    it.noGpsError()
            }

        }

        setContent {
            NiceWeatherAppTheme {
                MainScreen(locationCallback = locationCallback)
            }
        }

//        setUpPeriodicNotification()
    }

//    private fun setUpPeriodicNotification() {
//        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
//
//        val intent = Intent(this, AlarmRec::class.java)
//        intent.action = "MORNING_FORECAST"
//        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
//
//        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, "", pendingIntent)
//
//    }

    private fun getLocation(vm: MainScreenViewModel) {
        AppLocationProvider().getLocation(this, object : AppLocationProvider.LocationCallBack {
            override fun locationResult(location: Location?) {
                runOnUiThread {
                    location?.let {
                        vm.getWeatherData(it)
                    }
                }
            }
        })
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }
}
