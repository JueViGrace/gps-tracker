package com.jvg.gpsapp.shared.data.mappers.auth

import com.jvg.gpsapp.api.auth.model.response.AuthResponse
import com.jvg.gpsapp.types.auth.Session
import migrations.Gps_session

fun AuthResponse.toSession(): Session {
    return Session(
        id = id,
        accessToken = accessToken,
        refreshToken = refreshToken,
        active = false,
    )
}

fun AuthResponse.toDb(): Gps_session {
    return Gps_session(
        id = id,
        access_token = accessToken,
        refresh_token = refreshToken,
        active = false
    )
}
