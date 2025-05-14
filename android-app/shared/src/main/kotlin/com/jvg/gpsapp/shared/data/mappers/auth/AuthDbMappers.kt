package com.jvg.gpsapp.shared.data.mappers.auth

import com.jvg.gpsapp.types.auth.Session
import migrations.Gps_session

fun Gps_session.toSession(): Session {
    return Session(
        id = id,
        accessToken = access_token,
        refreshToken = refresh_token,
        active = active,
    )
}
