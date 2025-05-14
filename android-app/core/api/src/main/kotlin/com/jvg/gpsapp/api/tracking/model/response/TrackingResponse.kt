package com.jvg.gpsapp.api.tracking.model.response

import kotlinx.serialization.SerialName

data class TrackingResponse(
    @SerialName("latitude")
    val latitude: Double,
    @SerialName("longitude")
    val longitude: Double,
    @SerialName("altitude")
    val altitude: Double,
    @SerialName("time")
    val time: String,
    @SerialName("session_id")
    val sessionId: String,
)
