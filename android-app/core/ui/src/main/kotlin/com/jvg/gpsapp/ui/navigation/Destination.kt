package com.jvg.gpsapp.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Destination {
    @Serializable
    data object Root : Destination

    @Serializable
    data object Splash : Destination

    @Serializable
    data object AuthGraph : Destination

    @Serializable
    data object Auth : Destination

    @Serializable
    data object HomeGraph : Destination

    @Serializable
    data object Home : Destination
}
