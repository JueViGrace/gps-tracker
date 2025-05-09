package com.jvg.gpsapp.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class APIResponse<T>(
    @SerialName("status")
    val status: Int,
    @SerialName("description")
    val description: String,
    @SerialName("data")
    val data: T? = null,
    @SerialName("message")
    val message: String,
    @SerialName("time")
    val time: String,
)
