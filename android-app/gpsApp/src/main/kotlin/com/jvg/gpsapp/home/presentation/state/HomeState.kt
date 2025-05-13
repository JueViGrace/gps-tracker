package com.jvg.gpsapp.home.presentation.state

import com.jvg.gpsapp.types.tracking.Tracking

data class HomeState(
    val trackingLoading: Boolean = true,
    val tracking: Tracking? = null,
    val trackingList: List<Tracking> = emptyList(),
    val trackingError: Boolean = false,
)
