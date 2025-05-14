package com.jvg.gpsapp.shared.data.mappers.tracking

import com.jvg.gpsapp.types.tracking.Tracking
import com.jvg.gpsapp.util.Dates
import migrations.Gps_tracking

fun Gps_tracking.toTracking(): Tracking {
    return Tracking(
        latitude = latitude,
        longitude = longitude,
        altitude = altitude,
        time = Dates.parse(time),
    )
}
