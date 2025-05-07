package com.jvg.gpsapp.ui.navigation

import androidx.navigation.NavOptions

sealed interface NavigationAction {
    data class Navigate(
        val destination: Destination,
        val navOptions: NavOptions? = null
    ) : NavigationAction

    data class PopUntil(
        val destination: Destination,
        val inclusive: Boolean = false,
        val saveState: Boolean = false
    ) : NavigationAction

    data object NavigateUp : NavigationAction
}
