package com.tadjmerwan.niceweatherapp.models

import com.tadjmerwan.niceweatherapp.models.MeasurementUnits.Imperial
import com.tadjmerwan.niceweatherapp.models.MeasurementUnits.Metric

enum class MeasurementUnits {
    Metric,
    Imperial
}

fun MeasurementUnits.getTempSymb(): Char {
    return when (this) {
        Metric -> 'C'
        Imperial -> 'F'
    }
}

fun MeasurementUnits.getSpeedSymb(): String {
    return when (this) {
        Metric -> "Metre/Sec"
        Imperial -> "Miles/Hour."
    }
}