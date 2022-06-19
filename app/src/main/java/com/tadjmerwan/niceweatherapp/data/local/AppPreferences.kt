package com.tadjmerwan.niceweatherapp.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.tadjmerwan.niceweatherapp.Config
import com.tadjmerwan.niceweatherapp.models.MeasurementUnits
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val USER_PREFERENCES_NAME = "settings"
val Context.dataStore by preferencesDataStore(name = USER_PREFERENCES_NAME)

class AppPreferences(private val context: Context) {
    private val unitPrefKey = stringPreferencesKey("unit")

    suspend fun setUnits(units: MeasurementUnits) {
        context.dataStore.edit {
            it[unitPrefKey] = units.name
        }
    }

    val getUnits: Flow<MeasurementUnits> = context.dataStore.data
        .map { preferences ->
            var value = Config.DEFAULT_UNITS
            try {
                value =
                    MeasurementUnits.valueOf(preferences[unitPrefKey] ?: Config.DEFAULT_UNITS.name)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            value
        }

}