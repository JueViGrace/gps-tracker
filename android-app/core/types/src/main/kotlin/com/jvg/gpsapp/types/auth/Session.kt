package com.jvg.gpsapp.types.auth

data class Session(
    val id: String,
    val accessToken: String,
    val refreshToken: String,
    val active: Boolean,
)
