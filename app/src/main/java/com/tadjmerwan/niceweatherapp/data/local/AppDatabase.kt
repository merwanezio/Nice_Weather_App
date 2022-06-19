package com.tadjmerwan.niceweatherapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tadjmerwan.niceweatherapp.models.db.Forecast

@Database(entities = [Forecast::class], version = 1)
@TypeConverters(DBTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract val forecastDAO: ForecastDAO
}