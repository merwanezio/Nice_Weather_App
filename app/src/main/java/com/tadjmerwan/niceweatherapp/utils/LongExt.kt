package com.tadjmerwan.niceweatherapp.utils

import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

fun Long.getDayName(): String {
    val time = LocalDateTime.ofEpochSecond(this, 0, ZoneOffset.UTC)
    return time.dayOfWeek.name.lowercase().capitalize(Locale.getDefault())

}

fun Long.getTime(): String {
    val time = LocalDateTime.ofEpochSecond(this, 0, ZoneOffset.UTC)
    return time.hour.toString() + ":" + time.minute.toString()

}
