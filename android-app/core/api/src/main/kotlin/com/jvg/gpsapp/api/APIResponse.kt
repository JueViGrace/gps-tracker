package com.jvg.gpsapp.api

data class APIResponse<T>(
    val status: Int,
    val description: String,
    val data: T? = null,
    val message: String,
    val time: String,
)
