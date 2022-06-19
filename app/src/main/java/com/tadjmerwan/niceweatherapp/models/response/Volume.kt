package com.tadjmerwan.niceweatherapp.models.response

import com.google.gson.annotations.SerializedName

data class Volume(
    @SerializedName("1h") val lastHour: Float?,
)