package com.jvg.gpsapp.shared.data.mappers.tracking

import com.jvg.gpsapp.api.tracking.model.dto.TrackingDto
import com.jvg.gpsapp.types.tracking.Tracking
import com.jvg.gpsapp.util.Dates
import kotlinx.datetime.format

fun Tracking.toDto(): TrackingDto {
    return TrackingDto(
        latitude = latitude,
        longitude = longitude,
        altitude = altitude,
        time = time.format(Dates.dateTimeFormat)
    )
}
