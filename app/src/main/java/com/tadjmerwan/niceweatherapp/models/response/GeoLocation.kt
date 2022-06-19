package com.tadjmerwan.niceweatherapp.models.response

import com.google.gson.annotations.SerializedName

data class GeoLocationResponse(
    @SerializedName("name") val name: String?,
    @SerializedName("local_names") val localNames: Any?,
    @SerializedName("lat") val latitude: Float?,
    @SerializedName("lon") val longitude: Float?,
    @SerializedName("country") val country: String?, //"US"
    @SerializedName("state") val state: String?, //"CA"
)