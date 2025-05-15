package com.jvg.gpsapp.shared.data.mappers.tracking

import com.jvg.gpsapp.api.tracking.model.response.TrackingResponse
import com.jvg.gpsapp.types.tracking.Tracking
import com.jvg.gpsapp.util.Dates
import migrations.Gps_tracking

fun TrackingResponse.toDb(): Gps_tracking {
    return Gps_tracking(
        latitude = latitude,
        longitude = longitude,
        altitude = altitude,
        time = time,
        session_id = sessionId
    )
}

fun TrackingResponse.toTracking(): Tracking {
    return Tracking(
        latitude = latitude,
        longitude = longitude,
        altitude = altitude,
        time = Dates.parse(time),
    )
}