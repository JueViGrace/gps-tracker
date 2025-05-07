package com.jvg.gpsapp.shared.data.mappers.auth

import com.jvg.gpsapp.types.auth.Session
import migrations.Gps_session

fun Session.toDb(): Gps_session {
    return Gps_session(
        id = id,
        access_token = accessToken,
        refresh_token = refreshToken,
        active = active
    )
}
