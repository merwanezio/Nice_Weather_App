package com.tadjmerwan.niceweatherapp.models.response

import com.google.gson.annotations.SerializedName

data class Temperature(
    @SerializedName("day") val day: Float?,
    @SerializedName("min") val min: Float?,// not in feels like
    @SerializedName("max") val max: Float?,//not in feels like
    @SerializedName("night") val night: Float?,
    @SerializedName("eve") val evening: Float?,
    @SerializedName("morn") val morning: Float?,
)