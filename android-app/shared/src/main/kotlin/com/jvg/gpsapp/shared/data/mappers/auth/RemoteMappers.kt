package com.jvg.gpsapp.shared.data.mappers.auth

import com.jvg.gpsapp.api.auth.model.AuthResponse
import com.jvg.gpsapp.types.auth.Session

fun AuthResponse.toSession(): Session {
    return Session(
        id = id,
        accessToken = accessToken,
        refreshToken = refreshToken,
        active = false,
    )
}
