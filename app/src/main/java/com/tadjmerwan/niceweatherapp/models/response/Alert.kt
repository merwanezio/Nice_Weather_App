package com.tadjmerwan.niceweatherapp.models.response

import com.google.gson.annotations.SerializedName

data class Alert(
    @SerializedName("sender_name") val senderName: String?,
    @SerializedName("event") val event: String?,
    @SerializedName("start") val startDateTime: Long?,
    @SerializedName("end") val endDateTime: Long?,
    @SerializedName("description") val description: String?,
    @SerializedName("tags") val tags: List<String>?,
)

