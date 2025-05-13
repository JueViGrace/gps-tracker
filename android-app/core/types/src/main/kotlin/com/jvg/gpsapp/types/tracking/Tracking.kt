package com.jvg.gpsapp.types.tracking

import kotlinx.datetime.LocalDateTime

data class Tracking(
    val latitude: Double,
    val longitude: Double,
    val altitude: Double,
    val time: LocalDateTime,
)
