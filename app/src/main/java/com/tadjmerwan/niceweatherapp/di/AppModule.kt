package com.tadjmerwan.niceweatherapp.di

import android.app.Application
import androidx.room.Room
import com.tadjmerwan.niceweatherapp.Config
import com.tadjmerwan.niceweatherapp.Config.DB_NAME
import com.tadjmerwan.niceweatherapp.data.local.AppDatabase
import com.tadjmerwan.niceweatherapp.data.local.AppPreferences
import com.tadjmerwan.niceweatherapp.data.remote.OpenWeatherAPI
import com.tadjmerwan.niceweatherapp.data.remote.RetrofitClient
import com.tadjmerwan.niceweatherapp.repos.GeoInfoRepo
import com.tadjmerwan.niceweatherapp.repos.GeoInfoRepoImpl
import com.tadjmerwan.niceweatherapp.repos.WeatherRepo
import com.tadjmerwan.niceweatherapp.repos.WeatherRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRoomDB(app: Application): AppDatabase {
        return Room.databaseBuilder(app, AppDatabase::class.java, DB_NAME).build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return RetrofitClient().get()
    }

    @Provides
    @Singleton
    fun provideOpenWeatherAPI(retrofit: Retrofit): OpenWeatherAPI {
        return retrofit.create<OpenWeatherAPI>(OpenWeatherAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideCurrentWeatherRepo(
        db: AppDatabase,
        api: OpenWeatherAPI,
        ap: AppPreferences,
        appId: String
    ): WeatherRepo {
        return WeatherRepoImpl(api, db.forecastDAO, ap, appId)
    }

    @Provides
    @Singleton
    fun provideGeoInfoRepo(
        api: OpenWeatherAPI,
        appId: String
    ): GeoInfoRepo {
        return GeoInfoRepoImpl(api, appId)
    }

    @Provides
    @Singleton
    fun provideAppPreferences(app: Application): AppPreferences {
        return AppPreferences(app)
    }

    @Provides
    @Singleton
    fun provideAppID(app: Application): String {
        return Config.API_KEY(app)
    }
}