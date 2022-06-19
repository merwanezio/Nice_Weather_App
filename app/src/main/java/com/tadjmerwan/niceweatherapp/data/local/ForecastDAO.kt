package com.tadjmerwan.niceweatherapp.data.local

import androidx.room.*
import com.tadjmerwan.niceweatherapp.models.db.Forecast
import kotlinx.coroutines.flow.Flow


@Dao
interface ForecastDAO {

    @Query("SELECT * FROM city_forecasts")
    fun getAll(): Flow<List<Forecast>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg forcast: Forecast)

    @Delete
    suspend fun delete(forcast: Forecast)

    @Query("DELETE FROM city_forecasts")
    suspend fun deleteAll()

    @Query("SELECT * FROM city_forecasts WHERE latitude=:lat AND longitude=:lon")
    suspend fun getByLatLon(lat: Float, lon: Float): Forecast?
}